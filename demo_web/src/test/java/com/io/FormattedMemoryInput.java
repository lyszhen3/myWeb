package com.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by lys on 2017-08-29.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class FormattedMemoryInput {
    public static void main(String[] args) {
        try {
            DataInputStream in  = new DataInputStream(
                    new ByteArrayInputStream(BufferedInputFile.read("D:\\ideawork\\work1\\myWeb\\src\\test\\java\\com\\io\\FormattedMemoryInput.java").getBytes()));
            while (in.available()!=0)
            System.out.print((char)in.readByte());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
