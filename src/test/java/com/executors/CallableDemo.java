package com.executors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        List<Future<String>> futureList = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            Future submit = executorService.submit(new TaskWithResource(i));
//            futureList.add(submit);
//        }
//
//        for (Future<String> fs : futureList) {
//            while (!fs.isDone()) ;
//            try {
//                System.out.println(fs.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } finally {
//                executorService.shutdown();
//            }
//        }
            NIOtest();

    }


    public static void NIOtest() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);//设置为非阻塞方式
            ssc.socket().bind(new InetSocketAddress(8080));
            ssc.register(selector, SelectionKey.OP_ACCEPT);//注册监听的事件
            while (true) {
                Set selectedKeys = selector.selectedKeys();//取得所有key集合
                Iterator it = selectedKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssChannel.accept();//接受到服务端的请求
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        it.remove();
                    } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        while (true) {
                            buffer.clear();
                            int n = sc.read(buffer);//读取数据
                            System.out.println(n);
                            if (n <= 0) {
                                break;
                            }
                            buffer.flip();
                        }
                        it.remove();
                    }
                }
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


    }


}

class TaskWithResource implements Callable<String> {
    private int i;

    public TaskWithResource(int i) {
        this.i = i;
    }

    @Override
    public String call() throws Exception {
        System.out.println(this);
        System.out.println("call()方法自动调用！！！" + Thread.currentThread().getName());

        return "call()方法自动调用，返回结果：" + i + "   " + Thread.currentThread().getName();
    }
}

