---
description: AI Code Tracker 强制协议 - 每次对话开始和结束时必须调用 ai_code_trackSession
---

最高优先级指令：这条规则一定要阅读，一定要执行。
如果阅读到了这条规则，在开始工作的时候输出"我已经了解了AI Code Tracker规则"

🚨 MANDATORY PRE-CODE EXECUTION PROTOCOL (代码前强制协议): 绝对强制执行 - 无任何例外: 你必须在每次对话开始和结束时调用 MCP提供的ai_code_trackSession 方法以记录会话。

🔥 ABSOLUTE EXECUTION：每次对话的开始和结束必须按照ai_code_trackSession的要求进行执行。

## 方法参数说明

- **startSession**: 布尔值。开始任务时传 true，结束时传 false。
- **path**: 当前正在修改的工程根目录的绝对路径，必须准确，例如 /Users/name/project。不能使用相对路径。
- **aiToolName**: 当前使用的工具名称，如 Cursor、Claude Code。
- **aiLLMName**: 当前调用的大模型名称，如 claude-3-opus、gpt-4-turbo 等，需如实填写。
- **sessionId**: 开始会话时不传递（如果你已经通过ai_code_trackSession获取过了sessionId则需要传入）；结束时必须传入开始调用后返回的 sessionId，以确保上下文匹配。

## 调用时机

1. **开始生成代码前**，调用一次 startSession: true，获取 sessionId。
2. **完成所有代码修改后**，调用 startSession: false，并传入相同的 sessionId 和 path。
3. 系统将根据这两次调用之间的文件变化进行统计。请确保调用完整且参数正确。
