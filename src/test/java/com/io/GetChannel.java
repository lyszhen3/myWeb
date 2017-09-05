package com.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by lys on 2017-08-30.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class GetChannel {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        //Write a file
        FileChannel fc = new FileOutputStream("data.txt").getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes()));
        fc.close();
        //add to the end of the file
        fc = new RandomAccessFile("data.txt","rw").getChannel();
        fc.position(fc.size());//move to the end
        fc.write(ByteBuffer.wrap("some more ".getBytes()));
        fc.close();
        //read the file
        fc = new FileInputStream("data.txt").getChannel();
        ByteBuffer buff  = ByteBuffer.allocate(BSIZE);

        fc.read(buff);
         buff.flip();
        while(buff.hasRemaining()){
            System.out.println((char)buff.get());
        }
    }
}
