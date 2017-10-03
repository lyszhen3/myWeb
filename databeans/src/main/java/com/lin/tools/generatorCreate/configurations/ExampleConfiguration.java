package com.lin.tools.generatorCreate.configurations;

import com.lin.tools.generatorCreate.configurations.beans.ColumnValue;
import com.lin.tools.generatorCreate.configurations.beans.MethodValue;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

import static com.lin.tools.generatorCreate.configurations.ExampleConfiguration.ConditionTypeEnum.*;

/**
 * Created by pc on 2017/9/30.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class ExampleConfiguration extends DefaultConfiguration {
    public final static String mouldLocation;

    static {
        URL resource = ExampleConfiguration.class.getResource("");
        String path = resource.getPath();
        path = path.replace("target/classes/", "");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        int i = path.lastIndexOf("/");
        path = path.substring(0, i) + "/mould/ExampleMould";
        mouldLocation = path;
    }

    private List<ColumnValue> columnValues;

    public ExampleConfiguration(List<ColumnValue> collection) {
        this.columnValues = collection;
    }

    @Override
    public String createCode() {
        columnValues.forEach(c -> {
            StringBuilder condicationBd = new StringBuilder();
            ConditionTypeEnum[] typeEnums = values();
            for (ConditionTypeEnum typeEnum : typeEnums) {
                switch (typeEnum) {
                    case ISNULL: {
                        condicationBd.append("public Criteria ")
                                .append(typeEnum.value.replace("#{column}", nameFirstUp(c.getName())));
                        StringJoiner joiner = new StringJoiner(",","(",")");
                        for (int i = 0; i < typeEnum.paramNum; i++) {
                            joiner.add(c.getType()+" "+"value"+(i+1));
                        }
                        condicationBd.append(joiner.toString()).append("{");

                        break;
                    }
                }
            }
        });


        return null;
    }
    private String nameFirstUp(String name){
        String first = name.substring(0,1);
        name = name.replaceFirst(first,first.toUpperCase());
        return name;
    }
    enum ConditionTypeEnum {
        ISNULL("and#{column}IsNull", "is null", 0),
        ISNOTNULL("and#{column}IsNotNull", "is not null", 0),
        EQUEALTO("and#{column}dEqualTo", "=", 1),
        NOTEQUEALTO("and#{column}NotEqualTo", "<>", 1),
        GREATERTHAN("and#{column}GreaterThan", ">", 1),
        GREATERTHANOREQUALTO("and#{column}GreaterThanOrEqualTo", ">=", 1),
        LESSTHAN("and#{column}LessThan", "<", 1),
        LESSTHANOREQUALTO("and#{column}LessThanOrEqualTo", "<=", 1),
        IN("and#{column}In", "in", 1),
        NOTIN("and#{column}NotIn", "not in", 1),
        LIKE("and#{column}Like", "like", 1),
        BETWEEN("and#{column}Between", "between", 2),
        NOTBETWEEN("and#{column}NotBetween", "not between", 2);

        ConditionTypeEnum(String value, String operation, int paramNum) {
            this.value = value;
            this.operation = operation;
            this.paramNum = paramNum;
        }

        String value;
        String operation;
        int paramNum;
    }

    private void ColumnToMethod(List<MethodValue> list) {

    }

    private String suojin(int offset) {
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < offset; i++) {
            sbd.append(indent);
        }
        return sbd.toString();
    }

    public static void main(String[] args) {
        URL resource = ExampleConfiguration.class.getResource("");
        String path = resource.getPath();
        path = path.replace("target/classes/", "");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        int i = path.lastIndexOf("/");
        path = path.substring(0, i) + "/mould/ExampleMould";


    }


    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }


}
