package com.lin.tools.generatorCreate.configurations;

import com.lin.tools.generatorCreate.Beans.TableInfo;
import com.lin.tools.generatorCreate.configurations.beans.MethodValue;
import com.lin.tools.generatorCreate.run.StringParseUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import static com.lin.tools.generatorCreate.configurations.ExampleConfiguration.ConditionTypeEnum.values;

/**
 * Created by pc on 2017/9/30.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description oo
 * @since 3.0.0-SNAPSHOT
 */
public class ExampleConfiguration extends DefaultConfiguration {
    public final static String mouldLocation;

    static {
        URL resource = ExampleConfiguration.class.getResource("");
        String path = resource.getPath();

        int tindex = path.indexOf("target/classes/") + 14;
        path = path.substring(0, tindex) + "/mould/ExampleMould";
        mouldLocation = path;
    }

    private TableInfo info;

    public ExampleConfiguration(TableInfo info) {
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
                if (line.contains("#{andCondition}")) {
                    line = line.replace("#{andCondition}", createConditionCode());
                }
                codebd.append(line).append("\n");
            }
        } finally {
            reader.close();
            br.close();
        }

        return codebd.toString();
    }


    private String createConditionCode() {
        StringBuilder conditionCode = new StringBuilder();
        info.getList().forEach(c -> {
            StringBuilder condicationBd = new StringBuilder();
            ConditionTypeEnum[] typeEnums = values();
            for (ConditionTypeEnum typeEnum : typeEnums) {

                condicationBd.append(suojin(2)).append("public Criteria ")
                        .append(typeEnum.value.replace("#{column}", nameFirstUp(c.getName())));
                StringJoiner joiner = new StringJoiner(",", "(", ")");
                StringBuilder methodCodeBd = new StringBuilder();
                methodCodeBd.append(suojin(3)).append("addCriterion(\"")
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
                    if (i == typeEnum.paramNum - 1) {
                        methodCodeBd.append(",").append("\"").append(c.getName()).append("\"");
                    }
                }
                methodCodeBd.append(");").append("\n")
                        .append(suojin(3))
                        .append("return (Criteria) this;");
                condicationBd.append(joiner.toString())
                        .append("{").append("\n").append(methodCodeBd.toString())
                        .append("\n").append(suojin(2))
                        .append("}").append(row_2);
                conditionCode.append(condicationBd.toString());
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
        EQUEALTO("and#{column}EqualTo", "=", 1),
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


}
