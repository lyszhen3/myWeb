import type { ExtensionAPI } from "@earendil-works/pi-coding-agent";

/**
 * Vim-style :q / :q! to exit pi.
 *
 * Place this file in:
 *   ~/.pi/agent/extensions/vim-quit.ts   (global)
 *   .pi/extensions/vim-quit.ts           (project-local)
 */
export default function (pi: ExtensionAPI) {
  pi.on("input", async (event, ctx) => {
    const text = event.text.trim();

    if (text === ":q" || text === ":q!" || text === ":quit") {
      ctx.shutdown();
      return { action: "handled" };
    }
  });
}
