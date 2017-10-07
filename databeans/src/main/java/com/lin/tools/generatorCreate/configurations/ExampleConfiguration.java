package com.lin.tools.generatorCreate.configurations;

import com.lin.tools.generatorCreate.configurations.beans.ColumnValue;
import com.lin.tools.generatorCreate.configurations.beans.MethodValue;

import java.net.URL;
import java.util.*;

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
        StringBuilder conditionCode = new StringBuilder();
        columnValues.forEach(c -> {
            StringBuilder condicationBd = new StringBuilder();
            ConditionTypeEnum[] typeEnums = values();
            for (ConditionTypeEnum typeEnum : typeEnums) {
                switch (typeEnum) {
                    default: {
                        condicationBd.append("public Criteria ")
                                .append(typeEnum.value.replace("#{column}", nameFirstUp(c.getName())));
                        StringJoiner joiner = new StringJoiner(",", "(", ")");
                        StringBuilder methodCodeBd = new StringBuilder();
                        methodCodeBd.append(indent + indent + indent).append("addCriterion(\"")
                                .append(space)
                                .append(fieldFormat(c.getName()))
                                .append(space)
                                .append(typeEnum.operation)
                                .append("\"");

                        for (int i = 0; i < typeEnum.paramNum; i++) {
                            if (typeEnum == ConditionTypeEnum.LIKE) {
                                //like只能使用String类型
                                joiner.add("String" + space + "value" + (i + 1));
                            } else {
                                joiner.add(c.getType() + " " + "value" + (i + 1));
                            }
                            methodCodeBd.append(",").append("value").append(i + 1);
                            if(i == typeEnum.paramNum -1){
                                methodCodeBd.append(",").append(c.getName());
                            }
                        }
                        methodCodeBd.append(")").append("\n")
                                .append(indent + indent + indent)
                                .append("return (Criteria) this;");
                        condicationBd.append(joiner.toString())
                                .append("{").append("\n").append(methodCodeBd.toString())
                                .append("\n").append(indent + indent)
                                .append("}").append(row_2);
                       conditionCode.append(condicationBd.toString());
                        break;
                    }
                }
            }
        });


        return conditionCode.toString();
    }

    private static String fieldFormat(String field) {
        char[] chars = field.toCharArray();
        StringBuilder bd = new StringBuilder();
        for (char aChar : chars) {

            if ((int) aChar == (int) Character.toUpperCase(aChar)) {
                bd.append("_");
            }
            bd.append(Character.toLowerCase(aChar));
        }
        return bd.toString();
    }

    private String nameFirstUp(String name) {
        String first = name.substring(0, 1);
        name = name.replaceFirst(first, first.toUpperCase());
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
        String name = "niHaoA";
        String s = fieldFormat(name);
        System.out.println(s);

    }


    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }


}
