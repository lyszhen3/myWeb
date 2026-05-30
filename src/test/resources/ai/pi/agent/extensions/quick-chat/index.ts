/**
 * Quick Chat — Out-of-band Q&A for pi
 *
 * Calls the LLM in a separate process so responses NEVER enter
 * the current session's context window.
 *
 * Usage:
 *   /chat "帮我翻译这段话"              → LLM answer, zero context pollution
 *   /chat --model Qwen3-Plus "问题"    → Use specific model
 *   /chat --list                       → List available models
 *   ?你的问题                          → Prefix shortcut
 *   /quick calc 3*7                    → Built-in utilities (no LLM)
 *   /quick date                        → Current time
 *   /quick help                        → Show help
 */

import type { ExtensionAPI } from "@earendil-works/pi-coding-agent";
import { env } from "node:process";
import { existsSync, readFileSync } from "node:fs";
import { join } from "node:path";
import { homedir } from "node:os";

interface ModelEntry {
  provider: string;
  id: string;
  name: string;
  baseUrl: string;
  apiKey: string;
  api?: string;
}

interface LlmConfig {
  provider: string;
  baseUrl: string;
  key: string;
  model: string;
  api?: string;
}

export default function (pi: ExtensionAPI) {
  // Persisted selected model across /chat calls
  let selectedModel: string | null = null;

  // Read all available models from pi's models.json
  function readAvailableModels(): ModelEntry[] {
    const modelsPath = join(homedir(), ".pi", "agent", "models.json");
    if (!existsSync(modelsPath)) return [];

    try {
      const content = readFileSync(modelsPath, "utf-8");
      const config = JSON.parse(content) as {
        providers?: Record<string, {
          baseUrl?: string;
          apiKey?: string;
          api?: string;
          models?: Array<{ id: string; name?: string }>;
        }>;
      };

      if (!config.providers) return [];

      const models: ModelEntry[] = [];
      for (const [providerName, provider] of Object.entries(config.providers)) {
        if (provider.models) {
          for (const m of provider.models) {
            if (provider.apiKey) {
              models.push({
                provider: providerName,
                id: m.id,
                name: m.name || m.id,
                baseUrl: provider.baseUrl ?? "",
                apiKey: provider.apiKey,
                api: provider.api,
              });
            }
          }
        }
      }
      return models;
    } catch {
      return [];
    }
  }

  // Get provider + API key from pi's model configuration
  function getLlmConfig(
    ctx: Parameters<Parameters<typeof pi.on>[1]>[1],
    modelOverride?: string,
  ): LlmConfig | null {
    const allModels = readAvailableModels();

    // If a specific model is requested, find it in models.json
    if (modelOverride) {
      const matched = allModels.find(
        (m) => m.id.toLowerCase() === modelOverride.toLowerCase()
          || m.name.toLowerCase().includes(modelOverride.toLowerCase())
          || `${m.provider}/${m.id}`.toLowerCase().includes(modelOverride.toLowerCase()),
      );
      if (matched && matched.apiKey) {
        return {
          provider: matched.provider,
          baseUrl: matched.baseUrl,
          key: matched.apiKey,
          model: matched.id,
          api: matched.api,
        };
      }
      return null;
    }

    // Priority 1: use selected model
    if (selectedModel) {
      const matched = allModels.find(
        (m) => m.id === selectedModel || `${m.provider}/${m.id}` === selectedModel,
      );
      if (matched && matched.apiKey) {
        return {
          provider: matched.provider,
          baseUrl: matched.baseUrl,
          key: matched.apiKey,
          model: matched.id,
          api: matched.api,
        };
      }
    }

    // Priority 2: first available model from models.json
    if (allModels.length > 0 && allModels[0].apiKey) {
      const first = allModels[0];
      return {
        provider: first.provider,
        baseUrl: first.baseUrl,
        key: first.apiKey,
        model: first.id,
        api: first.api,
      };
    }

    // Priority 3: ctx.modelRegistry
    const model = ctx.model;
    if (!model) return null;

    const modelRegistry = ctx.modelRegistry;
    if (!modelRegistry) return null;

    const registryModels = modelRegistry.getAllModels?.() ?? [];
    const matched = registryModels.find(
      (m) => m.provider === model.provider && m.id === model.id,
    );

    if (matched) {
      const providerKey = (matched as any).apiKey || (matched as any).providerApiKey;
      if (providerKey) {
        return {
          provider: model.provider,
          baseUrl: (matched as any).baseUrl ?? "",
          key: providerKey,
          model: model.id,
        };
      }
    }

    // Priority 4: fallback to env
    if (env.ANTHROPIC_API_KEY) {
      return { provider: "anthropic", baseUrl: "", key: env.ANTHROPIC_API_KEY, model: model.id };
    }
    if (env.OPENAI_API_KEY) {
      return { provider: "openai", baseUrl: "", key: env.OPENAI_API_KEY, model: model.id };
    }

    return null;
  }

  // Build the correct API URL based on provider config
  function buildUrl(baseUrl: string, api?: string): string {
    if (!baseUrl) {
      return "https://api.openai.com/v1/chat/completions";
    }

    // Already a full endpoint
    if (baseUrl.includes("/chat/completions") || baseUrl.includes("/v1/messages") || baseUrl.endsWith("/messages")) {
      return baseUrl;
    }

    // Anthropic Messages API
    if (api === "anthropic-messages") {
      return `${baseUrl}/v1/messages`;
    }

    // Default: OpenAI Chat Completions
    return `${baseUrl}/v1/chat/completions`;
  }

  // Call LLM out-of-band via direct API (no session involvement)
  async function callLlmOutOfBand(
    question: string,
    systemPrompt: string,
    maxTokens: number,
    ctx: Parameters<Parameters<typeof pi.on>[1]>[1],
    modelOverride?: string,
  ): Promise<string | null> {
    const llmConfig = getLlmConfig(ctx, modelOverride);
    if (!llmConfig) {
      return null;
    }

    const { provider, baseUrl, key, model, api } = llmConfig;
    const url = buildUrl(baseUrl, api);

    try {
      // Anthropic Messages API
      if (api === "anthropic-messages" || provider === "anthropic") {
        const resp = await fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "x-api-key": key,
            "anthropic-version": "2023-06-01",
          },
          body: JSON.stringify({
            model,
            max_tokens: maxTokens,
            system: systemPrompt,
            messages: [{ role: "user", content: question }],
          }),
        });

        if (!resp.ok) {
          const errBody = await resp.text().catch(() => "");
          throw new Error(`API 错误 (HTTP ${resp.status}): ${errBody || resp.statusText}`);
        }

        const data = (await resp.json()) as {
          content: Array<{ type: string; text: string }>;
        };
        return data.content.find((c) => c.type === "text")?.text ?? null;
      }

      // OpenAI Chat Completions API (default)
      const resp = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${key}`,
        },
        body: JSON.stringify({
          model,
          max_tokens: maxTokens,
          messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: question },
          ],
        }),
      });

      if (!resp.ok) {
        const errBody = await resp.text().catch(() => "");
        throw new Error(`API 错误 (HTTP ${resp.status}): ${errBody || resp.statusText}`);
      }

      const data = (await resp.json()) as {
        choices?: Array<{ message?: { content?: string }; finish_reason?: string }>;
        error?: { message?: string };
      };

      if (data.error) {
        throw new Error(`API 错误: ${data.error.message}`);
      }

      return data.choices?.[0]?.message?.content ?? null;
    } catch (err) {
      throw err;
    }
  }

  // Parse /chat arguments: extract --model, --list flags and remaining question
  function parseChatArgs(raw: string): { model?: string; list: boolean; question: string } {
    let model: string | undefined;
    let list = false;
    let question = raw;

    // --model <name>
    const modelMatch = raw.match(/--model\s+(\S+)/);
    if (modelMatch) {
      model = modelMatch[1];
      question = question.replace(/--model\s+\S+\s*/, "").trim();
    }

    // --list / -l
    if (/--list|-l\b/.test(raw)) {
      list = true;
      question = question.replace(/--list|-l\b/g, "").trim();
    }

    return { model, list, question };
  }

  // ============================================================
  // /chat "question" - Quick Q&A, out-of-band (RECOMMENDED)
  // Uses direct API call — zero session involvement.
  // ============================================================

  pi.registerCommand("chat", {
    description: "Quick Q&A without touching session context. Usage: /chat [--model <name>] [--list] <question>",
    handler: async (args, ctx) => {
      if (!args || args.trim() === "") {
        ctx.ui.notify("用法: /chat [--model <name>] [--list] <你的问题>", "warning");
        return;
      }

      const parsed = parseChatArgs(args.trim());

      // --list: show available models
      if (parsed.list) {
        const models = readAvailableModels();
        if (models.length === 0) {
          ctx.ui.notify("未找到可用模型。请检查 ~/.pi/agent/models.json", "warning");
          return;
        }
        const lines = models.map((m, i) => {
          const current = m.id === selectedModel ? " ◀ 当前" : "";
          return `  ${i + 1}. ${m.provider}/${m.id}  —  ${m.name}${current}`;
        });
        const text = `可用模型:\n${lines.join("\n")}\n\n用法: /chat --model <名称> <问题>\n当前: ${selectedModel || "(默认第一个)"}`;
        ctx.ui.notify(text, "info");
        return;
      }

      if (!parsed.question) {
        ctx.ui.notify("用法: /chat [--model <name>] <你的问题>", "warning");
        return;
      }

      // Update selected model if specified
      if (parsed.model) {
        const models = readAvailableModels();
        const found = models.find(
          (m) => m.id.toLowerCase() === parsed.model!.toLowerCase()
            || m.name.toLowerCase().includes(parsed.model!.toLowerCase())
            || `${m.provider}/${m.id}`.toLowerCase().includes(parsed.model!.toLowerCase()),
        );
        if (found) {
          selectedModel = found.id;
        } else {
          ctx.ui.notify(`⚠️ 未找到模型: ${parsed.model}。用 /chat --list 查看可用模型。`, "error");
          return;
        }
      }

      const modelName = selectedModel || parsed.model || "(default)";
      const shortQ = parsed.question.length > 40 ? parsed.question.slice(0, 40) + "..." : parsed.question;
      ctx.ui.setStatus("quick-chat", `💬 ${modelName}: ${shortQ}`);

      let answer: string | null = null;
      try {
        answer = await callLlmOutOfBand(
          parsed.question,
          "你是一个有帮助的助手。请用中文回答。回答简洁，最多3-5句话。",
          512,
          ctx,
          parsed.model,
        );
      } catch (err) {
        ctx.ui.setStatus("quick-chat", undefined);
        const msg = err instanceof Error ? err.message : String(err);
        ctx.ui.notify(`❌ ${msg}`, "error");
        return;
      }

      ctx.ui.setStatus("quick-chat", undefined);

      if (answer) {
        ctx.ui.notify(answer, "info");
      } else {
        ctx.ui.notify("⚠️ 未检测到 API key。请检查 ~/.pi/agent/models.json 配置，或设置 ANTHROPIC_API_KEY / OPENAI_API_KEY 环境变量。", "error");
      }
    },
  });

  // ============================================================
  // Input interception: ? prefix for quick chat
  // IMPORTANT: pi may still record the raw input in the session
  // before this handler runs. If that's the case, use /chat instead.
  // ============================================================

  pi.on("input", async (event, ctx) => {
    if (event.source !== "interactive") return;

    // ?prefix for quick chat (but not ?? which is bash history)
    if (event.text.startsWith("?") && !event.text.startsWith("??")) {
      const question = event.text.slice(1).trim();
      if (!question) return { action: "continue" };

      ctx.ui.setStatus("quick-chat", "💬 " + question);

      // Fire-and-forget: don't block the input loop
      (async () => {
        try {
          const answer = await callLlmOutOfBand(
            question,
            "你是一个有帮助的助手。请用中文简洁回答，最多2-3句话。",
            256,
            ctx,
          );

          ctx.ui.setStatus("quick-chat", undefined);

          if (answer) {
            ctx.ui.notify(answer, "info");
          } else {
            ctx.ui.notify("⚠️ 未检测到 API key，无法回答。", "warning");
          }
        } catch (err) {
          ctx.ui.setStatus("quick-chat", undefined);
          const msg = err instanceof Error ? err.message : String(err);
          ctx.ui.notify(`❌ ${msg}`, "error");
        }
      })();

      return { action: "handled" };
    }

    return { action: "continue" };
  });

  // ============================================================
  // /quick - Built-in utilities (no LLM needed)
  // ============================================================

  pi.registerCommand("quick", {
    description: "Quick utilities without LLM (date, calc, help)",
    handler: async (args, ctx) => {
      if (!args) {
        ctx.ui.notify(
          "Quick utilities:\n" +
          "  /quick date       — 当前时间\n" +
          "  /quick calc 2*3   — 简单计算\n" +
          "  /chat '...'       — LLM 问答（不占上下文）\n" +
          "  /chat --list      — 列出可用模型\n" +
          "  /chat --model M Q — 指定模型提问\n" +
          "  ?问题             — 快捷提问",
          "info",
        );
        return;
      }

      const parts = args.split(/\s+/);
      const cmd = parts[0].toLowerCase();
      const input = parts.slice(1).join(" ");

      try {
        switch (cmd) {
          case "date":
          case "time": {
            const now = new Date();
            ctx.ui.notify(`📅 ${now.toLocaleString("zh-CN", { timeZone: "Asia/Shanghai" })}`, "info");
            break;
          }
          case "calc":
          case "math": {
            const sanitized = input.replace(/[^0-9+\-*/().%\s]/g, "");
            const result = Function(`"use strict"; return (${sanitized})`)();
            ctx.ui.notify(`🔢 ${input} = ${result}`, "info");
            break;
          }
          case "help": {
            ctx.ui.notify(
              "用法:\n" +
              "  /chat \"问题\"           — LLM 快速问答（不占上下文）\n" +
              "  /chat --list             — 列出可用模型\n" +
              "  /chat --model <模型> <问题> — 指定模型提问\n" +
              "  ?问题                    — 同上，快捷方式\n" +
              "  /quick date              — 当前时间\n" +
              "  /quick calc 2*3          — 简单计算\n" +
              "  /quick help              — 显示帮助",
              "info",
            );
            break;
          }
          default:
            ctx.ui.notify(`未知命令: ${cmd}。试试 /quick help`, "warning");
        }
      } catch (err) {
        ctx.ui.notify(`错误: ${(err as Error).message}`, "error");
      }
    },
  });
}
