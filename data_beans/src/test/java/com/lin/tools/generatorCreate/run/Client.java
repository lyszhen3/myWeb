package com.lin.tools.generatorCreate.run;


import com.lin.tools.generatorCreate.Beans.TableInfo;
import com.lin.tools.generatorCreate.configurations.BeanConfiguration;
import com.lin.tools.generatorCreate.configurations.DefaultConfiguration;
import com.lin.tools.generatorCreate.configurations.ExampleConfiguration;
import com.lin.tools.generatorCreate.configurations.MapperConfiguration;
import com.lin.tools.generatorCreate.run.create.Create;

import java.io.File;
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
                        ex.setPackagePath(getBasePackage(file.getPath()));
                        ex.setOutPutPath(file.getPath());
                        DefaultConfiguration mp = new MapperConfiguration(info);
                        mp.setPackagePath(getBasePackage(file.getPath()));
                        mp.setOutPutPath(file.getPath());
                        DefaultConfiguration bn = new BeanConfiguration(info);
                        bn.setPackagePath(getBasePackage(file.getPath()));
                        bn.setOutPutPath(file.getPath());
                        Create create = new Create();
                        List<DefaultConfiguration> list = new ArrayList<>();
                        list.add(ex);
                        list.add(mp);
                        list.add(bn);
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
    public static String getBasePackage(String path){
        int i = path.indexOf("java\\")+5;
        return path.substring(i,path.length()).replace("\\",".");
    }

}
