# scoop 使用方式

1. scoop 安装
   1. 使用powershell
   2. 前置命令

      ``` powershell
      Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
      ```

   3. 指定位置安装

      ```powershell
      irm get.scoop.sh -outfile 'install.ps1'
      .\install.ps1 -ScoopDir 'D:\Applications\Scoop' -NoProxy
      ```

2. aria2 加快下载速度
   1. 安装aria2

      ```powershell
      scoop install aria2
      ```

   2. aria2 dns 问题解决

      ```powershell
      scoop config aria2-options '--async-dns=false'
      ```
