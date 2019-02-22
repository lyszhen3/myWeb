package fieldconvert;

import com.alibaba.fastjson.annotation.JSONField;

import java.lang.annotation.Annotation;

/**
 * Created by lys on 3/15/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class StringConvertProcessor extends AbstractProcessor<JSONField,String>{

    
    @Override
    public String convert(String oriValue, JSONField annotation) {


        return oriValue;
    }

    public static void main(String[] args) {

    }
}
