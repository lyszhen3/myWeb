package com.lin.tools.generatorCreate.configurations.beans;

/**
 * Created by pc on 2017/9/30.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class ColumnValue {
    public ColumnValue(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public ColumnValue() {

    }

    String name;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}