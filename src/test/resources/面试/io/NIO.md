# NIO

## channel

> 通道，和实体交互，实体可以是文件，网络套接字等

把数据读入到`buffer`，也可以从`buffer`中读取数据到`channel`

**FileChannel**:读写文件  
**DatagramChannel**:UDP协议网络通信  
**SocketChannel**: 监听TCP链接

## buffer

**主要使用方式**：

1. 将数据写入`buffer`
2. 调用buffer.flip()
3. 将数据从buffer读出来
4. 调用buffer.clear()或buffer.compact()

**总结**：   
    写`buffer`，会跟踪写了多少数据。需要读的时候，需要调用flip()来将`buffer`从写模式切换到读模式，只能读取写入的数据，而非
`buffer`中所有数据.  
    当数据读写完后， 使用 `buffer.clear()`或`buffer.compact()` 来清空buffer数据。  
    **buffer.clear()**:只清楚已经读取的数据  
    **buffer.compact()**:清除所有数据

## selector
> 采集各个`channel`的状态(或者说事件)。将`channel`注册到`selector`,设置关心的事件，调用`select()`方法，等待事件发生