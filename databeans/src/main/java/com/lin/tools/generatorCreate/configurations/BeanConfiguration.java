package com.lin.tools.generatorCreate.configurations;

import com.lin.tools.generatorCreate.Beans.TableInfo;
import com.lin.tools.generatorCreate.configurations.beans.ColumnValue;
import com.lin.tools.generatorCreate.run.StringParseUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Created by pc on 2017-10-10.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class BeanConfiguration extends DefaultConfiguration {


    public final static String mouldLocation;

    static {
        URL resource = ExampleConfiguration.class.getResource("");
        String path = resource.getPath();
        int tindex = path.indexOf("target/classes/") + 14;
        path = path.substring(0, tindex) + "/mould/BeanMould";
        mouldLocation = path;

    }

    private TableInfo info;

    public BeanConfiguration(TableInfo info) {
        this.info = info;
    }


    @Override
    public String createCode() throws IOException {
        FileReader reader = new FileReader(mouldLocation);
        BufferedReader br = new BufferedReader(reader);
        StringBuilder codebd = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("#{package}")) {
                    line = line.replace("#{package}", getPackagePath());
                }
                if (line.contains("#{bean}")) {
                    //替换#{bean}首字母大写
                    line = line.replace("#{bean}", StringParseUtil.firstUpCase(info.getTableName())
                    );
                }
                if (line.contains("#{date}")) {
                    line = line.replace("#{date}", LocalDate.now().toString());
                }
                if (line.contains("#{innerCode}")) {
                    line = line.replace("#{innerCode}", createInnerCode());
                }
                codebd.append(line).append("\n");
            }
        } finally {
            reader.close();
            br.close();
        }

        return codebd.toString();
    }


    private String createInnerCode() {
        StringBuilder innerCodeBd = new StringBuilder();
        innerCodeBd.append(suojin(1));

        StringBuilder getSetBd = new StringBuilder();
        for (ColumnValue c : info.getList()) {
            StringBuilder columnBd = new StringBuilder();
            columnBd.append("\n").append(suojin(1))
                    .append("/**")
                    .append(c.getComment())
                    .append("*/");//注释
            if (c.getKey()) {
                //如果是主键
                columnBd.append("\n")
                        .append(suojin(1))
                        .append("@Id")
                        .append("\n")
                        .append(suojin(1))
                        .append("@GeneratedValue(generator=\"JDBC\")");
            }
            if (c.getType().equals("Date")) {
                c.setType("java.util." + c.getType());
            }
            columnBd.append("\n")
                    .append(suojin(1))
                    .append("private")
                    .append(space)
                    .append(c.getType())
                    .append(space).append(c.getName()).append(";");
            innerCodeBd.append(columnBd.toString());
            /**public void setXx(String xx){
             *      this.xx = xx;
             *      }
             * public String getXx(){
             *     return this.xx;
             * }
             */
            getSetBd.append("\n")
                    .append(suojin(1))
                    .append("public")
                    .append(space)
                    .append("void")
                    .append(space)
                    .append("set")
                    .append(StringParseUtil.firstUpCase(c.getName()))
                    .append("(")
                    .append(c.getType())
                    .append(space)
                    .append(c.getName()).append(")").append("{")
                    .append("\n").append(suojin(2))
                    .append("this.").append(c.getName()).append(space).append("=").append(c.getName()).append(";")
                    .append("\n").append(suojin(1)).append("}");//set
            getSetBd.append("\n").append(suojin(1))
                    .append("public").append(space).append(c.getType()).append(space)
                    .append("get").append(StringParseUtil.firstUpCase(c.getName())).append("(){").append("\n")
                    .append(suojin(2)).append("return").append(space).append("this.").append(c.getName()).append(";")
                    .append("\n").append(suojin(1)).append("}");
        }
        innerCodeBd.append("\n").append(getSetBd.toString());
        return innerCodeBd.toString();
    }

    @Override
    public void setOutPutPath(String outPutPath) {
        super.setOutPutPath(outPutPath + "/beans/" + StringParseUtil.firstUpCase(info.getTableName()) + ".java");
    }

    @Override
    public void setPackagePath(String packagePath) {

        super.setPackagePath(packagePath + ".beans;");
    }


}
