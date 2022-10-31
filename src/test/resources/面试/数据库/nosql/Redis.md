# Redis

## 持久化

1. RDB
   * 持久化的二进制文件dump.rdb
   * 命令和配置文件
     * 命令:save(阻塞)/bgsave(非阻塞)
     * 配置文件:  
         save 900 1  //900s有一个key被修改时保存  
         save 300 10  //300s内有10个key被修改时保存  
         save 60 10000 //60s内有10000个key被修改时保存
   * fork一个进程进行数据复制,当中间数据变更时,会丢失
2. AOF
   * 将每一步redis的修改操作记录(日志)append到相应的文件中,appendonly.aof
   * 先将数据写到缓冲区,再把缓冲区的数据flush到磁盘,叫fsync
   * flush方式有三种:  
      appendfsync always //每次写操作都flush，影响性能  
      appendfsync everysec //每秒flush  
      appendfsync no //消极等待OS刷新(一般30s),可能丢失数据
   * bgrewirteaof(aof重写)
     * 解决aof文件越来越大
     * 命令互相抵消,整理,重写后文件变小
     * 混合模式:将当前全量数据以rdb得方式写入appendonly.aof文件前半部分,  
     后半部分继续append 命令
     * 触发机制:  
        1. auto-aof-rewrite-percentage 100(aof重写百分比)  
        2. auto-aof-rewrite-min-size 64mb(aof重写最小数据大小)  
        e.g. 64mb + 64mb(引用2)*100(引用1)% , 没64,128,192mb触发一次aof重写