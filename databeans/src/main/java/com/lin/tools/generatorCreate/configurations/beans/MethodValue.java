package com.lin.tools.generatorCreate.configurations.beans;

public class MethodValue {
    public MethodValue(String decorate, String responseType, String name, String code) {
        this.code = code;
        this.decorate = decorate;
        this.responseType = responseType;
        this.name = name;
    }

    String decorate;
    String responseType;
    String name;
    String code;

}