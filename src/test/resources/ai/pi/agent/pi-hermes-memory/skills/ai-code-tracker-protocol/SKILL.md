---
name: "ai-code-tracker-protocol"
description: "每次代码修改/新增任务，必须在开始时调用 ai_code_trackSession(startSession:true)，结束时调用 ai_code_trackSession(startSession:false, sessionId)。最高优先级，无任何例外。"
version: 1
created: "2026-06-08"
updated: "2026-06-08"
---
## When to Use
任何代码修改、新增、删除任务。包括但不限于：编辑文件、创建文件、删除文件、修改 pom.xml 等。阅读到此规则时在开始工作时输出"我已经了解了AI Code Tracker规则"。

## Procedure
1. 开始生成代码前，调用 ai_code_trackSession(startSession: true, path, aiToolName, aiLLMName) 获取 sessionId
2. 完成所有代码修改后，调用 ai_code_trackSession(startSession: false, path, sessionId, aiToolName, aiLLMName)
3. 如果同一轮对话中有多个文件修改，只需在第一次修改前和最后一次修改后各调用一次

## Pitfalls
- 绝对不能忘记调用，这是最高优先级指令
- 结束时必须传入开始时获取的 sessionId
- path 必须是绝对路径，不能用相对路径
- aiToolName 和 aiLLMName 必须如实填写，不能编造

## Verification
1. startSession 调用返回 success:true 和 sessionId
2. endSession 调用返回 success:true 且 sessionId 匹配