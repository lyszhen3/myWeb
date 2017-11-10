package com.lin.tools.generatorCreate.run;

import com.lin.tools.generatorCreate.Beans.TableInfo;
import com.lin.tools.generatorCreate.configurations.beans.ColumnValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public static final String priMarykeyReg = "PRIMARY KEY.(\\([^\\(\\)]*\\))";
    public static final String typeReg = "%s\\s(\\w[^\\s]*)\\s";
    public static final String commentReg = "%s.*?COMMENT\\s('.*?')";//非贪婪
    public static HashMap<String,String> correMap = new HashMap(){{
         /**
         * jdbcType:TINYINT,SMALLINT,MEDIUMINT,INT,INTEGER --javaType:Integer
         * jdbcType:BIGINT -- javaType:Long
         * jdbcType:FLOAT--javaType:Float
         * jdbcType:DOUBLE--javaType:Double
         * jdbcType:DATE ,DATETIME ,TIMESTAMP--javaType:Date
         * jdbcType:CHAR,VARCHAR,TINYTEXT ,TEXT --javaType:String
         */
        put("Integer","TINYINT,SMALLINT,MEDIUMINT,INT,INTEGER");
        put("Long","BIGINT");
        put("Float","FLOAT");
        put("Double","DOUBLE");
        put("Date","DATE,DATETIME,TIMESTAMP");
        put("String","CHAR,VARCHAR,TINYTEXT ,TEXT");

    }};


    //外键暂且忽略
//    public static final String foreignKey = "key.*(\\(`[^`]*`\\))";
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
        String group = tm.find()?tm.group(1):null;
        if(group == null){
            throw new RuntimeException("获取不到表名");
        }
        String tableName = group.substring(1,group.length()-1);
        System.out.println(tableName);
        Pattern keyp = Pattern.compile(priMarykeyReg);
        Matcher keym = keyp.matcher(ddl);
        String pkgroup = keym.find()?keym.group(1):null;
        if(pkgroup == null){
            throw  new RuntimeException("获取不到主键");
        }
        String keyNames = pkgroup.substring(1,pkgroup.length()-1);

        String[] keyArr = keyNames.split(",");//`a`,`b`

        List<String> column = all.stream()
                .parallel()
                .filter(c -> !c.equals(tableName))
                .distinct()
                .collect(Collectors.toList());
        column.forEach(System.out::println);
        TableInfo info = new TableInfo();
        info.setTableName(underLineCamelCase(tableName));
        List<ColumnValue> cList = new ArrayList<>();
        info.setList(cList);
        column.forEach(c->{
            Pattern typep = Pattern.compile(String.format(typeReg,"`"+c+"`"));
            Matcher typem = typep.matcher(ddl);
            String type = typem.find()?typem.group(1):null;
            if(type == null){
                return;
            }
            String finalType = type.substring(0,type.indexOf("(")>0?type.indexOf("("):type.length());

            ColumnValue cp = new ColumnValue();


            cp.setName(underLineCamelCase(c));
            fi:for (Map.Entry<String, String> typeEntry : correMap.entrySet()) {
                String value = typeEntry.getValue();
                String[] split = value.split(",");
                for (String s : split) {
                    if(s.equals(finalType.trim().toUpperCase())){
                        cp.setType(typeEntry.getKey());
                        break fi;
                    }
                }
            }
            for (String s : keyArr) {
                if(("`"+c+"`").equals(s)){
                    cp.setKey(true);
                }
            }
            if(cp.getType() == null){
                throw new RuntimeException(String.format("数据库属性:%s暂且不支持转化",finalType));
            }
            Pattern cmp = Pattern.compile(String.format(commentReg,"`"+c+"`"));
            Matcher cmm = cmp.matcher(ddl);
            String comment = cmm.find()?cmm.group(1):null;
            if(comment != null){
                cp.setComment(comment.substring(1,comment.length()-1));
                System.out.println(cp.getComment());
            }
            cList.add(cp);

        });

        return info;
    }

    private static String underLineCamelCase(String origin){
        String[] split = origin.split("_");
        StringBuilder cameBd = new StringBuilder(split[0]);
        for (int i = 1; i < split.length; i++) {
            char[] chars = split[i].toCharArray();
            chars[0]-=32;
            cameBd.append((chars));
        }
        return cameBd.toString();
    }
    public static String firstUpCase(String value){
        char[] chars = value.toCharArray();
        chars[0]-=32;
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        String xx = "";
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("2");
        list.add("5");
        correMap.forEach((s,s2)->{
            System.out.println(s);
            System.out.println(s2);
        });
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);
        String format = String.format(commentReg, "`id`");


    }


}
