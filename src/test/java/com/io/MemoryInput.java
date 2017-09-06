package com.io;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by lys on 2017-08-29.
 *卧槽这个叫什么
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class MemoryInput {
    public static void main(String[] args) throws IOException {
        StringReader in = new StringReader(BufferedInputFile.read("D:\\ideawork\\work1\\myWeb\\src\\test\\java\\com\\io\\MemoryInput.java"));
        int c;
        while((c=in.read())!=-1){
            System.out.print((char)c);
        }
    }
}
