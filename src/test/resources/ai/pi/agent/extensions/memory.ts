import type { ExtensionAPI } from "@earendil-works/pi-coding-agent";
import { Type } from "typebox";
import * as fs from "node:fs";
import * as path from "node:path";
import * as os from "node:os";

const MEMORY_FILE = path.join(os.homedir(), ".pi", "agent", "memory.json");

interface MemoryEntry {
  content: string;
  createdAt: string;
  category?: string;
}

interface MemoryStore {
  entries: MemoryEntry[];
}

function loadMemory(): MemoryStore {
  try {
    if (fs.existsSync(MEMORY_FILE)) {
      return JSON.parse(fs.readFileSync(MEMORY_FILE, "utf-8"));
    }
  } catch {
    // ignore
  }
  return { entries: [] };
}

function saveMemory(store: MemoryStore): void {
  const dir = path.dirname(MEMORY_FILE);
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir, { recursive: true });
  }
  fs.writeFileSync(MEMORY_FILE, JSON.stringify(store, null, 2), "utf-8");
}

function formatMemoryList(entries: MemoryEntry[]): string {
  if (entries.length === 0) return "No memories stored.";
  return entries
    .map((e, i) => `${i + 1}. [${e.category || "general"}] ${e.content} (added ${e.createdAt})`)
    .join("\n");
}

export default async function (pi: ExtensionAPI) {
  // Load memory at session start
  const memory = loadMemory();

  // Inject memory into system prompt at the start of each turn
  pi.on("before_agent_start", async (event, ctx) => {
    if (memory.entries.length === 0) return;
    const memoryBlock = [
      "## User Memory (persistent across sessions)",
      ...memory.entries.map((e) => `- [${e.category || "general"}] ${e.content}`),
    ].join("\n");
    return {
      systemPrompt: event.systemPrompt + "\n\n" + memoryBlock,
    };
  });

  // Tool: remember
  pi.registerTool({
    name: "remember",
    label: "Remember",
    description: "Save a persistent memory that will be available in all future sessions.",
    promptSnippet: "Save user preference or fact to persistent memory",
    promptGuidelines: [
      "Use remember when the user asks you to remember something for future sessions.",
    ],
    parameters: Type.Object({
      content: Type.String({ description: "The memory content to save" }),
      category: Type.Optional(Type.String({ description: "Optional category (e.g., preference, project, rule)" })),
    }),
    async execute(_toolCallId, params) {
      const store = loadMemory();
      const entry: MemoryEntry = {
        content: params.content,
        category: params.category,
        createdAt: new Date().toISOString().slice(0, 10),
      };
      store.entries.push(entry);
      saveMemory(store);
      return {
        content: [{ type: "text", text: `Memory saved (${store.entries.length} total):\n- [${entry.category || "general"}] ${entry.content}` }],
        details: { count: store.entries.length },
      };
    },
  });

  // Tool: recall
  pi.registerTool({
    name: "recall",
    label: "Recall",
    description: "List all stored memories, optionally filtered by category.",
    promptSnippet: "List stored persistent memories",
    promptGuidelines: [
      "Use recall when the user asks what you remember or wants to review stored memories.",
    ],
    parameters: Type.Object({
      category: Type.Optional(Type.String({ description: "Filter by category" })),
    }),
    async execute(_toolCallId, params) {
      const store = loadMemory();
      let entries = store.entries;
      if (params.category) {
        entries = entries.filter((e) => e.category?.toLowerCase() === params.category!.toLowerCase());
      }
      return {
        content: [{ type: "text", text: formatMemoryList(entries) }],
        details: { count: entries.length },
      };
    },
  });

  // Tool: forget
  pi.registerTool({
    name: "forget",
    label: "Forget",
    description: "Delete a memory by its index number (from recall list).",
    promptSnippet: "Delete a stored memory by index",
    promptGuidelines: [
      "Use forget when the user asks to remove a specific memory. The index comes from recall output.",
    ],
    parameters: Type.Object({
      index: Type.Number({ description: "The 1-based index of the memory to delete (from recall list)" }),
    }),
    async execute(_toolCallId, params) {
      const store = loadMemory();
      const idx = params.index - 1;
      if (idx < 0 || idx >= store.entries.length) {
        return {
          content: [{ type: "text", text: `Invalid index ${params.index}. Use /recall to see valid indices.` }],
          details: { error: "invalid_index" },
          isError: true,
        };
      }
      const removed = store.entries.splice(idx, 1)[0];
      saveMemory(store);
      return {
        content: [{ type: "text", text: `Removed: [${removed.category || "general"}] ${removed.content}\n${store.entries.length} memories remaining.` }],
        details: { count: store.entries.length },
      };
    },
  });

  // Tool: memory-clear
  pi.registerTool({
    name: "memory_clear",
    label: "Clear Memory",
    description: "Delete all stored memories. Requires confirmation.",
    promptSnippet: "Clear all persistent memories",
    promptGuidelines: [
      "Use memory_clear only when the user explicitly asks to clear ALL memories.",
    ],
    parameters: Type.Object({
      confirm: Type.Boolean({ description: "Must be true to confirm clearing all memories" }),
    }),
    async execute(_toolCallId, params) {
      if (!params.confirm) {
        return {
          content: [{ type: "text", text: "To clear all memories, set confirm=true." }],
          isError: true,
        };
      }
      saveMemory({ entries: [] });
      return {
        content: [{ type: "text", text: "All memories cleared." }],
        details: { count: 0 },
      };
    },
  });
}
