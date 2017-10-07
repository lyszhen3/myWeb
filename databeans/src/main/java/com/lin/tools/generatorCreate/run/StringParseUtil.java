package com.lin.tools.generatorCreate.run;

import com.lin.tools.generatorCreate.Beans.TableInfo;
import com.lin.tools.generatorCreate.configurations.beans.ColumnValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by pc on 2017-10-03.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class StringParseUtil {
//    public static final String tableAndcolumnReg="(?<=`)(.+?)(?=`)";
    public static final String tableAndColumnReg = "`[^`]*`";
    public static final String tableNameReg = "TABLE.(`[^`]*`)";
    public static final String keyReg = "PRIMARY KEY.{2}(`[^`]*`)";
    /**
     * 解析ddl
     *
     * @param ddl 表信息 like
     *            <p>CREATE TABLE `account` (
     *            `id` bigint(20) NOT NULL AUTO_INCREMENT,
     *            `name` varchar(64) NOT NULL,
     *            `email` varchar(128) DEFAULT NULL,
     *            `hash_password` varchar(255) DEFAULT NULL,
     *            PRIMARY KEY (`id`)
     *            ) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;</p>
     * @return
     */
    public static TableInfo parseDDL(String ddl) {
        List<String> all = new ArrayList<>();
        Pattern pattern = Pattern.compile(tableAndColumnReg);
        Matcher matcher = pattern.matcher(ddl);
        while (matcher.find()){
            String cv = matcher.group();
            all.add(cv.substring(1,cv.length()-1));
        }
        Pattern tp = Pattern.compile(tableNameReg);
        Matcher tm = tp.matcher(ddl);
        tm.find();
        String group = tm.group(1);
        String tableName = group.substring(1,group.length()-1);
        System.out.println(tableName);
        Pattern keyp = Pattern.compile(keyReg);
        Matcher keym = keyp.matcher(ddl);
        keym.find();
        String group1 = keym.group(1);
        String keyName = group1.substring(1,group1.length()-1);
        System.out.println(keyName);
        List<String> column = all.stream()
                .parallel()
                .filter(c -> !c.equals(tableName) && !c.equals(keyName))
                .collect(Collectors.toList());
        column.add(0,keyName);
        column.forEach(System.out::println);
        TableInfo info = new TableInfo();
        info.setTableName(tableName);
        column.forEach(c->{


        });

        return info;
    }

    public static void main(String[] args) {
        String xx = "CREATE TABLE `account` ( `id` bigint(20) NOT NULL AUTO_INCREMENT,`name` varchar(64) NOT NULL,`email` varchar(128) DEFAULT NULL,`hash_password` varchar(255) DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8";
        parseDDL(xx);
        String xxx = "我的天";


    }
    static enum ColumnType{
        /**
         * jdbcType:TINYINT,SMALLINT,MEDIUMINT,INT,INTEGER --javaType:Integer
         * jdbcType:BIGINT -- javaType:Long
         * jdbcType:FLOAT--javaType:Float
         * jdbcType:DOUBLE--javaType:Double
         * jdbcType:DATE ,DATETIME ,TIMESTAMP--javaType:Date
         * jdbcType:CHAR,VARCHAR,TINYTEXT ,TEXT --javaType:String
         */
    }

}
