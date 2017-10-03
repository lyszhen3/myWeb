package com.lin.tools.generatorCreate.configurations;

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
    private String resource;
    public abstract String createCode();
}
