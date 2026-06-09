import type { ExtensionAPI } from "@earendil-works/pi-coding-agent";
import { readFile as fsReadFile, writeFile as fsWriteFile, access as fsAccess } from "node:fs/promises";
import { constants } from "node:fs";
import { createHash } from "node:crypto";
import iconv from "iconv-lite";

const utf8Decoder = new TextDecoder('utf-8', { fatal: true });

/**
 * Cache for file encodings to avoid repeated detection.
 */
const encodingCache = new Map<string, "utf8" | "gbk">();

async function detectEncoding(filePath: string): Promise<"utf8" | "gbk"> {
    const buffer = await fsReadFile(filePath);
    if (buffer.length === 0) return "utf8";
    try {
        utf8Decoder.decode(buffer);
        return "utf8";
    } catch {
        try {
            iconv.decode(buffer, "gbk");
            return "gbk";
        } catch {
            return "utf8";
        }
    }
}

async function getEncoding(filePath: string): Promise<"utf8" | "gbk"> {
    const cached = encodingCache.get(filePath);
    if (cached !== undefined) return cached;
    const encoding = await detectEncoding(filePath);
    encodingCache.set(filePath, encoding);
    return encoding;
}

/**
 * Read file with auto-detection of UTF-8 vs GBK encoding.
 * Returns the decoded text content.
 */
async function readWithEncoding(filePath: string): Promise<string> {
    const rawBuffer = await fsReadFile(filePath);
    if (rawBuffer.length === 0) return "";
    try {
        utf8Decoder.decode(rawBuffer);
        encodingCache.set(filePath, "utf8");
        return rawBuffer.toString("utf8");
    } catch {
        const gbkText = iconv.decode(rawBuffer, "gbk");
        encodingCache.set(filePath, "gbk");
        return gbkText;
    }
}

/**
 * Write text content back to file with original encoding.
 */
async function writeWithEncoding(filePath: string, content: string): Promise<void> {
    const encoding = await getEncoding(filePath);
    if (encoding === "gbk") {
        const buffer = iconv.encode(content, "gbk");
        await fsWriteFile(filePath, buffer);
    } else {
        await fsWriteFile(filePath, content, "utf8");
    }
}

/**
 * Generate a short content hash (matching pi-hashline-edit format).
 */
function contentHash(text: string): string {
    return createHash('md5').update(text).digest('hex').slice(0, 2);
}

/**
 * Parse a LINE#HASH anchor, returning line number or null.
 */
function parseAnchor(anchor: string): number | null {
    const match = anchor.match(/^(\d+)#[a-f0-9]+$/);
    return match ? parseInt(match[1], 10) : null;
}

/**
 * Apply a single edit operation to lines array.
 * Returns { lines: newLines, firstChanged: number, lastChanged: number } or null on failure.
 */
function applyEdit(lines: string[], edit: {
    op: string;
    pos?: string;
    end?: string;
    lines?: string[] | null;
    oldText?: string;
    newText?: string;
}): { lines: string[]; firstChanged: number; lastChanged: number } | string {
    // Defensive: ensure newContent is always an array
    const rawLines = edit.lines ?? [];
    const newContent: string[] = typeof rawLines === "string"
        ? rawLines.split(/\r?\n/)
        : Array.isArray(rawLines)
            ? rawLines
            : [String(rawLines)];

    if (edit.op === "replace_text") {
        if (!edit.oldText) return "replace_text requires oldText";
        const joined = lines.join('\n');
        const idx = joined.indexOf(edit.oldText);
        if (idx === -1) return `replace_text: oldText not found: ${edit.oldText.substring(0, 40)}`;
        const before = joined.substring(0, idx);
        const after = joined.substring(idx + edit.oldText.length);
        const newJoined = before + (edit.newText ?? "") + after;
        return { lines: newJoined.split('\n'), firstChanged: 1, lastChanged: newJoined.split('\n').length };
    }

    if (!edit.pos) return "Operation requires pos anchor";
    const posLine = parseAnchor(edit.pos);
    if (posLine === null) return `Invalid pos anchor: ${edit.pos}`;
    const posIdx = posLine - 1; // 0-indexed

    if (edit.op === "replace") {
        const endAnchor = edit.end;
        let endIdx: number;
        if (endAnchor) {
            const endLine = parseAnchor(endAnchor);
            if (endLine === null) return `Invalid end anchor: ${endAnchor}`;
            endIdx = endLine - 1;
        } else {
            endIdx = posIdx;
        }
        // Clamp
        const safeStart = Math.max(0, Math.min(posIdx, lines.length));
        const safeEnd = Math.min(endIdx, lines.length - 1);
        const newLines = [...lines];
        newLines.splice(safeStart, safeEnd - safeStart + 1, ...newContent);
        return { lines: newLines, firstChanged: safeStart + 1, lastChanged: safeStart + Math.max(newContent.length, 1) };
    }

    if (edit.op === "append") {
        const safePos = Math.max(0, Math.min(posIdx, lines.length));
        const newLines = [...lines];
        newLines.splice(safePos + 1, 0, ...newContent);
        return { lines: newLines, firstChanged: safePos + 2, lastChanged: safePos + newContent.length };
    }

    if (edit.op === "prepend") {
        const safePos = Math.max(0, Math.min(posIdx, lines.length - 1));
        const newLines = [...lines];
        newLines.splice(safePos, 0, ...newContent);
        return { lines: newLines, firstChanged: safePos + 1, lastChanged: safePos + newContent.length };
    }

    return `Unknown operation: ${edit.op}`;
}

/**
 * Format content with hashline anchors (matching pi-hashline-edit format).
 */
function formatHashline(content: string, offset?: number, limit?: number): string {
    const allLines = content.split('\n');
    const startLine = (offset ?? 1) - 1; // Convert 1-indexed to 0-indexed
    const endLine = limit !== undefined ? startLine + limit : allLines.length;
    const selectedLines = allLines.slice(startLine, endLine);

    const formattedLines = selectedLines.map((line, i) => {
        const lineNum = startLine + i + 1;
        const hash = contentHash(line);
        return `${String(lineNum).padStart(6, ' ')}#${hash}:${line}`;
    });

    const totalLines = allLines.length;
    let result = formattedLines.join('\n');

    if (selectedLines.length < totalLines) {
        const nextOffset = endLine + 1;
        result += `\n\n[Showing lines ${startLine + 1}-${endLine} of ${totalLines}. Use offset=${nextOffset} to continue.]`;
    }

    return result;
}

/**
 * Format the changed region of a file as hashline anchors for edit response.
 */
function formatChangedRegion(lines: string[], firstChanged: number, lastChanged: number, totalLines: number): string {
    const startIdx = Math.max(0, firstChanged - 3); // Show 3 context lines before
    const endIdx = Math.min(lines.length, lastChanged + 2); // Show 2 context lines after
    const region = lines.slice(startIdx, endIdx);
    const formattedLines = region.map((line, i) => {
        const lineNum = startIdx + i + 1;
        const hash = contentHash(line);
        return `${String(lineNum).padStart(6, ' ')}#${hash}:${line}`;
    });
    let result = `--- Anchors ${firstChanged}-${lastChanged} ---\n`;
    result += formattedLines.join('\n');
    if (startIdx > 0) {
        result = `... (${startIdx} lines omitted)\n` + result;
    }
    if (endIdx < totalLines) {
        result += `\n... (${totalLines - endIdx} lines omitted)`;
    }
    return result;
}

export default function (pi: ExtensionAPI) {
    pi.on("session_start", async () => {
        encodingCache.clear();
    });

    // Intercept read tool results to fix GBK encoding errors
    pi.on("tool_result", async (event, ctx) => {
        if (event.toolName !== "read") return;
        if (!event.isError) return;

        // Check if the error is about invalid UTF-8 or binary file
        const contentText = event.content?.map(c => c.type === 'text' ? c.text : '').join('') || '';
        if (!contentText.includes("invalid UTF-8") &&
            !contentText.includes("binary file") &&
            !contentText.includes("GBK")) {
            return;
        }

        // Extract file path from the tool input
        const input = event.input as { path?: string };
        if (!input.path) return;

        try {
            // Re-read with GBK support
            const textContent = await readWithEncoding(input.path);
            const offset = (event.input as { offset?: number }).offset;
            const limit = (event.input as { limit?: number }).limit;
            const formatted = formatHashline(textContent, offset, limit);

            return {
                content: [{ type: "text", text: formatted }],
                isError: false,
            };
        } catch (err) {
            // If GBK decode also fails, keep the original error
            return undefined;
        }
    });

    // Intercept edit tool results to fix GBK encoding errors
    // Actually applies the edit to GBK files instead of just re-reading
    pi.on("tool_result", async (event, ctx) => {
        if (event.toolName !== "edit") return;
        if (!event.isError) return;

        const contentText = event.content?.map(c => c.type === 'text' ? c.text : '').join('') || '';
        if (!contentText.includes("invalid UTF-8") &&
            !contentText.includes("binary file")) {
            return;
        }

        const input = event.input as { path?: string; edits?: unknown[] };
        if (!input.path) return;
        if (!input.edits || !Array.isArray(input.edits)) {
            return;
        }

        try {
            // Step 1: Read the file with GBK encoding
            const textContent = await readWithEncoding(input.path);
            // Detect and normalize line endings
            const hasCRLF = textContent.includes('\r\n');
            const normalizedContent = textContent.replace(/\r\n/g, '\n');
            let lines = normalizedContent.split('\n');
            const totalLines = lines.length;

            // Step 2: Apply each edit operation
            let firstChanged = lines.length;
            let lastChanged = 1;
            for (const rawEdit of input.edits) {
                const edit = rawEdit as {
                    op: string;
                    pos?: string;
                    end?: string;
                    lines?: string[] | null;
                    oldText?: string;
                    newText?: string;
                };
                const result = applyEdit(lines, edit);
                if (typeof result === "string") {
                    return {
                        content: [{ type: "text", text: `[GBK edit failed] ${result}` }],
                        isError: true,
                    };
                }
                lines = result.lines;
                firstChanged = Math.min(firstChanged, result.firstChanged);
                lastChanged = Math.max(lastChanged, result.lastChanged);
            }

            // Step 3: Write the file back with original (GBK) encoding and line endings
            let newContent = lines.join('\n');
            if (hasCRLF) {
                newContent = newContent.replace(/\n/g, '\r\n');
            }
            await writeWithEncoding(input.path, newContent);



            // Step 4: Return a success response with the changed region
            const changedRegion = formatChangedRegion(lines, firstChanged, lastChanged, lines.length);

            return {
                content: [{ type: "text", text:
                    `[GBK file edited successfully]\n` +
                    `File: ${input.path}\n` +
                    `Encoding: GBK (preserved)\n` +
                    `Lines changed: ${firstChanged}-${lastChanged} (total lines: ${lines.length}, was ${totalLines})\n\n` +
                    changedRegion
                }],
                isError: false,
            };
        } catch (err) {
            const errMsg = err instanceof Error ? err.message : String(err);
            return {
                content: [{ type: "text", text: `[GBK edit error] ${errMsg}` }],
                isError: true,
            };
        }
    });
}
