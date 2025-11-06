# vsCode vim
>
> 这个玩意可以说非常的恶心

## 如何让可视化块模式（Ctrl+v）修改为 Ctrl+q

1. 先要把ctrl+v 给vim管理  
    a. settings 里设置

    ```json
    "vim.useCtrlKeys": true
    ```

    b. 或者settings 里设置添加  

    ```json
    "vim.handleKeys": {
    //添加<C-v> 给vim管理
    "<C-v>": true
    }
    ```

2. 修改keyboard shortcuts(vsCode快捷键)  
    a. 找到extension.vim_ctrl+v 修改快捷键为Ctrl+q
    b. 修改vim快捷键映射  

    ```json
    "vim.normalModeKeyBindingsNonRecursive": [
        // 添加此映射，让 Ctrl+Q 执行 Vim 的原生 Ctrl+V (可视化块模式)
      {
        "before": [
        "<C-q>"
        ],
      "after": [
        "<C-v>"
        ]
      }

    ]
    ```
