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
        String path = "D:\\workspace\\self\\myWeb\\suiyiTest\\src\\main\\java\\io\\BasicFileOutput.java";
        BufferedReader in  = new BufferedReader(new FileReader(path));

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
