package com.lin.tools.generatorCreate.configurations;

import com.lin.tools.generatorCreate.Beans.TableInfo;
import com.lin.tools.generatorCreate.run.StringParseUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

/**
 * Created by pc on 2017-10-10.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class MapperConfiguration extends DefaultConfiguration {

    public final static String mouldLocation;

    static {
        URL resource = ExampleConfiguration.class.getResource("/");
        String path = resource.getPath();
        path = path+ "mould/MapperMould";
        mouldLocation = path;
    }
    private TableInfo info;

    public MapperConfiguration(TableInfo info) {
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
                if(line.contains("#{beanSource}")){
                    String beanSource = getPackagePath().replace(".mappers;", ".beans."
                            + StringParseUtil.firstUpCase(info.getTableName()));
                    line = line.replace("#{beanSource}",beanSource);
                }

                codebd.append(line).append("\n");
            }
        } finally {
            reader.close();
            br.close();
        }

        return codebd.toString();
    }

    @Override
    public void setPackagePath(String packagePath) {
        super.setPackagePath(packagePath+".mappers;");
    }

    @Override
    public void setOutPutPath(String outPutPath) {
        super.setOutPutPath(outPutPath+"/mappers/"+StringParseUtil.firstUpCase(info.getTableName())+"Mapper.java");
    }
}
