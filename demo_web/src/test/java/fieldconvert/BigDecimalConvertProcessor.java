package fieldconvert;

import com.alibaba.fastjson.annotation.JSONField;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lys on 3/13/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class BigDecimalConvertProcessor extends AbstractProcessor<JSONField, BigDecimal> {
    /**
     * 保留小数得位数规则
     */
    public final static String CUT_REG = "^#(\\d+)";
    public final static RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    @Override
    public  BigDecimal convert(BigDecimal oriValue, Annotation annotation) {

        JSONField jsonField = (JSONField) annotation;
        if (oriValue != null) {
            return convertValue(oriValue, jsonField.format());
        }
        return null;

    }

    private BigDecimal convertValue(BigDecimal bigDecimal, String format) {

        return getScalesNum(format) == null ? bigDecimal : bigDecimal.setScale(getScalesNum(format), ROUNDING_MODE);
    }

    private static Integer getScalesNum(String format) {
        Pattern p = Pattern.compile(CUT_REG);
        Matcher m = p.matcher(format);
        if (m.find()) {
            String group = m.group(1);
            return Integer.parseInt(group);
        }
        return null;
    }

}
