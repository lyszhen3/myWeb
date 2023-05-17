# diff操作

1. vs [temp](文件名) 创建一个临时文件,两个窗口都要输入,:diffthis
   1. 这里可以设置vimrc文件 set splitright vs创建的新窗口将在右边,就不用执行指针移动窗口了
2. 如果比较两个文件,使用:vert diffs[plit] filename
   3. vert水平打开的意思,diffs和diffsplit一样
3. 移动到下一处]c 上一处 [c
4. 关闭 diffoff