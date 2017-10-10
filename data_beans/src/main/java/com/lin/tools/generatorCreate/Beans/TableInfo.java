package com.lin.tools.generatorCreate.Beans;


import com.lin.tools.generatorCreate.configurations.beans.ColumnValue;

import java.util.List;

public class TableInfo {
    private String tableName;
    private List<ColumnValue> list;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnValue> getList() {
        return list;
    }

    public void setList(List<ColumnValue> list) {
        this.list = list;
    }
}
