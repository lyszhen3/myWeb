scoop 使用方式

1. scoop 安装
   1. 使用powershell
   2. 前置命令   
   ```
   Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
   ```
   3. 指定位置安装
   ```
   irm get.scoop.sh -outfile 'install.ps1'
   .\install.ps1 -ScoopDir 'D:\Applications\Scoop' -NoProxy
   ```

1. aria2 加快下载速度
   1. 安装aria2
   ```
   scoop install aria2
   ```
   2. aria2 dns 问题解决
   ```
   scoop config aria2-options '--async-dns=false'
   ```
   