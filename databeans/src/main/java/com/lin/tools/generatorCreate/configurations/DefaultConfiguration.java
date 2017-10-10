package com.lin.tools.generatorCreate.configurations;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by pc on 2017/9/30.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public abstract class DefaultConfiguration   {
    public static final String indent ="    ";
    public static final String row_2 = "\n\n";
    public static final String space = " ";
    public static final String extendsStr = "extends";
    public static final String implementsStr = "implements";
    private Object resource;
    private String outPutPath;
    private String packagePath;
    public abstract String createCode() throws IOException;

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public String getOutPutPath() {
        return outPutPath;
    }

    public void setOutPutPath(String outPutPath) {
        this.outPutPath = outPutPath;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }


    protected String suojin(int offset) {
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < offset; i++) {
            sbd.append(indent);
        }
        return sbd.toString();
    }
}
