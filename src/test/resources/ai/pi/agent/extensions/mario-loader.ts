/**
 * Mario Running Loader — Emoji Animation (Terminal-Safe)
 *
 * Uses setWorkingIndicator — pi's built-in animation engine.
 * Guaranteed to work in all terminals.
 */

import type { ExtensionAPI, ExtensionContext } from "@earendil-works/pi-coding-agent";

// Mario-themed emoji running animation — 4 frames
const FRAMES: string[] = [
  "\x1b[38;2;227;0;0m🏃\x1b[0m💨 🍄",
  "🍄 \x1b[38;2;227;0;0m🏃\x1b[0m💨",
  "\x1b[38;2;227;0;0m🏃\x1b[0m💨 ⭐",
  "⭐ \x1b[38;2;227;0;0m🏃\x1b[0m💨",
];

export default function (pi: ExtensionAPI) {
  let enabled = true;

  const start = (ctx: ExtensionContext) => {
    if (!enabled) {
      ctx.ui.setWorkingIndicator(undefined);
      ctx.ui.setStatus("mario", undefined);
      return;
    }
    ctx.ui.setWorkingIndicator({
      frames: FRAMES,
      intervalMs: 250,
    });
    ctx.ui.setStatus("mario", ctx.ui.theme.fg("accent", "🍄 Mario"));
  };

  pi.on("session_start", async (_e, ctx) => { start(ctx); });

  pi.registerCommand("mario", {
    description: "Toggle Mario loading animation",
    handler: async (args, ctx) => {
      const a = args.trim().toLowerCase();
      if (a === "on") { enabled = true; start(ctx); ctx.ui.notify("🍄 Mario on!", "info"); }
      else if (a === "off") { enabled = false; ctx.ui.setWorkingIndicator(undefined); ctx.ui.setStatus("mario", undefined); ctx.ui.notify("Default spinner", "info"); }
      else { enabled = !enabled; if (enabled) start(ctx); else { ctx.ui.setWorkingIndicator(undefined); ctx.ui.setStatus("mario", undefined); } ctx.ui.notify(enabled ? "🍄 Mario on!" : "Default spinner", "info"); }
    },
  });
}
