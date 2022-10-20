# Mysql
## Innodb
### 事务的ACID特性
1. Atomicity(原子性)
   1. 通过undo.log实现回滚
2. Consistency(一致性)
3. Isolation(隔离性)
   1. 读未提交
      1. 读事务允许读写事务,未提交的写事务允许读事务
   2. 读已提交
      1. mvcc
         * 每次select重新生成readView
   3. 可重复读
      1. mvcc
         * 第一次select生成readView 
   4. 序列化
      1. 串行化执行
4. Durability(持久性)
   1. redo.log
      1. 刷盘的时候如果突然宕机,可以通过redo.log重新写入磁盘
5. mvcc
      > readView有下列属性
   1. 已提交最大事务id
   2. 活跃中的事务id
   3. 当前最大事务id
   * 其他事务可以通过readView看到小于等于已提交最大事务id得事务(可见),  
   大于已提交最大事务id小于等于当前最大事务id为活跃id(不可见,当前事务可以看到自己的未提交事务),  
   大于当前最大事务id(不可见)
## buffer pool
1. 

## 基础问题
