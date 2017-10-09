package com.lin.tools.generatorCreate.run;


import com.lin.tools.generatorCreate.Beans.TableInfo;
import com.lin.tools.generatorCreate.configurations.DefaultConfiguration;
import com.lin.tools.generatorCreate.configurations.ExampleConfiguration;
import com.lin.tools.generatorCreate.run.create.Create;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pc on 2017/9/30.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Client {
    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        System.out.println("请输入文件输出地址");
        File file = new File(scan.next());
        scan.nextLine();
        StringBuilder bd = new StringBuilder();
        try {
            if (file.exists()) {
                System.out.println("请输入表DDL");
                while (scan.hasNextLine()){
                    String line = scan.nextLine();
                    bd.append(line);
                    if(line.endsWith(";")){
                        TableInfo info = StringParseUtil.parseDDL(bd.toString());
                        DefaultConfiguration ex = new ExampleConfiguration(info);
                        ex.setPackagePath("com.lin.data.examples;");
                        ex.setOutPutPath(file.getPath()+"/examples/"+StringParseUtil.firstUpCase(info.getTableName())+"Example.java");
                        Create create = new Create();
                        List<DefaultConfiguration> list = new ArrayList<>();
                        list.add(ex);
                        create.setConfigurations(list);
                        create.createNow();


                        break;
                    }

                }
            }else{
                throw new RuntimeException("文件路径不存在");
            }
        }finally {
            scan.close();
        }
    }

}
