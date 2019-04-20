package io;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.Charset;

/**
 * Created by lys on 2017-08-29.
 * 格式化输出
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class FormattedMemoryInput {
    static String path = "D:\\workspace\\self\\myWeb\\suiyiTest\\src\\main\\java\\io\\FormattedMemoryInput.java";
    public static void main(String[] args) {

       arrayByteReader();
       byteReader();
    }

    public static void arrayByteReader(){

        try {
            DataInputStream in  = new DataInputStream(
                    new ByteArrayInputStream(BufferedInputFile.read(path).getBytes(Charset.forName("UTF-8"))));
            while (in.available()!=0) {
                System.out.print((char)in.readByte());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void byteReader(){
        try {
            DataInputStream in = new DataInputStream((new BufferedInputStream(new FileInputStream(path))));
            while (in.available() != 0){
                System.out.print((char)in.readByte());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
