package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by lys on 2017-08-31.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class BufferToText {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        FileChannel fc = new FileOutputStream("data2.txt").getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes()));
        fc.close();
        fc = new FileInputStream("data2.txt").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        //Doesn,t work
        System.out.println(buff.asCharBuffer());
        //Decode using this system`s default Charset
        buff.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using"+encoding+":"+ Charset.forName(encoding).decode(buff));
        // Or,we could encode with something that will print;
        fc = new FileOutputStream("data3.txt").getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes("UTF-16BE")));//如果不转换成UTF-16BE使用asCharBuffer读出会乱码，同样如果写入的格式是UTF-16BE 文件看起来会乱码

        fc.close();
        //Now try reading again;
        fc = new FileInputStream("data3.txt").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
        //Use a CharBuffer to write through;
        fc = new FileOutputStream("data4.txt").getChannel();
        buff = ByteBuffer.allocate(24);// more than needed
        buff.asCharBuffer().put("some text");
        fc.write(buff);
        fc.close();
        //read and display;
        fc = new FileInputStream("data4.txt").getChannel();
        buff.clear();
        fc.read(buff);
        buff.flip();
        System.out.println(buff.asCharBuffer());
    }
}
