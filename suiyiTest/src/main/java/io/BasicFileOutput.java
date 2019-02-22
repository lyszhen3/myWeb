package io;

import java.io.*;

/**
 * Created by lys on 2017-08-29.
 *我的天哪
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class BasicFileOutput {
    static String file = "BasicFileOutPut.out";

    public static void main(String[] args) throws IOException {
        BufferedReader in  = new BufferedReader(new FileReader("D:\\ideawork\\work1\\myWeb\\src\\test\\java\\com\\io\\BasicFileOutput.java"));

        PrintWriter out  =new PrintWriter(new BufferedWriter(new FileWriter(file)));
        String s;
        int linCount = 1;
        while((s = in.readLine())!=null){
            out.println(linCount+++": "+s);
        }
        out.close();
        System.out.println(BufferedInputFile.read(file));
    }
}
