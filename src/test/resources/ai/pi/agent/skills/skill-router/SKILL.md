---
name: skill-router
description: "当用户使用中文提问或描述需求时自动触发，将中文意图映射到对应的英文 Skill 并执行。Chinese intent router - automatically maps Chinese user queries to the correct English skill. Triggers on any Chinese language input including: 代码、架构、流程、调用、报错、Bug、异常、影响、安全、重构、重命名、拆分、提取、移动、索引、分析、Wiki、浏览器、网页、截图、填表、写代码、审查、规范。"
license: MIT
---

# Skill Router — 中文意图路由

## 目的

当用户使用中文提出需求时，自动识别意图并路由到对应的英文 Skill 执行。本 Skill 是一个元级路由器，自身不执行具体任务。

## 路由表

根据用户中文表述的语义意图，匹配对应的 Skill 并读取执行：

| 中文意图（语义匹配，非精确关键词）                                | 目标 Skill | 路径 |
|--------------------------------------------------|---|---|
| 代码怎么运行的、架构是什么、调用链、执行流程、谁调用了、代码探索、某功能怎么实现的        | gitnexus-exploring | `C:\Users\alilys\.aone_copilot\skills\gitnexus-exploring\SKILL.md` |
| 改了会不会出问题、影响范围、安全吗、依赖分析、会破坏什么、改动风险                | gitnexus-impact-analysis | `C:\Users\alilys\.aone_copilot\skills\gitnexus-impact-analysis\SKILL.md` |
| 报错了、Bug、异常、为什么失败、追踪错误、出错原因、调试                    | gitnexus-debugging | `C:\Users\alilys\.aone_copilot\skills\gitnexus-debugging\SKILL.md` |
| 重命名、提取方法/模块、拆分类、重构、移动文件、抽象                       | gitnexus-refactoring | `C:\Users\alilys\.aone_copilot\skills\gitnexus-refactoring\SKILL.md` |
| 索引代码库、重新分析、生成Wiki、GitNexus状态、清理索引                | gitnexus-cli | `C:\Users\alilys\.aone_copilot\skills\gitnexus-cli\SKILL.md` |
| GitNexus怎么用、有什么工具、知识图谱查询、MCP资源                   | gitnexus-guide | `C:\Users\alilys\.aone_copilot\skills\gitnexus-guide\SKILL.md` |
| 打开网页、填表单、点击按钮、截图、爬取网页、自动化浏览器、测试网页、登录网站           | agent-browser | `C:\Users\alilys\.aone_copilot\skills\agent-browser\SKILL.md` |
| 编写、写代码、重构、重构代码、写代码时的规范、审查代码质量、避免过度设计、最小改动原则、编码准则 | karpathy-guidelines | `C:\Users\alilys\.aone_copilot\skills\karpathy-guidelines\SKILL.md` |

## 执行步骤

1. **识别意图**：分析用户的中文输入，理解其语义意图（不要仅做关键词匹配，要理解上下文含义）
2. **匹配 Skill**：根据上方路由表，找到最匹配的 Skill
3. **读取 Skill**：使用 `read_file` 读取匹配到的 Skill 文件的完整内容
4. **执行 Skill**：严格按照该 Skill 文档中的指令执行用户的请求

## 注意事项

- 匹配时应理解语义而非简单关键词匹配。例如"这个方法改了安全吗" → impact-analysis，而非 exploring
- 如果匹配到多个 Skill，优先选择与用户核心意图最相关的一个
- 如果用户意图不匹配任何已知 Skill，正常回答即可，不要强行路由
- 新增 Skill 时只需在路由表中添加一行即可
