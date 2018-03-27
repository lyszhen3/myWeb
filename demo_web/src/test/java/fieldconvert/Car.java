package fieldconvert;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lys on 3/15/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Car {
    @JSONField()
    private String name;
    @JSONField(format = "#2")
    private BigDecimal xx;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getXx() {
        return xx;
    }

    public void setXx(BigDecimal xx) {
        this.xx = xx;
    }

    public static void main(String[] args) {
        Car car = new Car();
        car.setName("ni");
        car.setXx(new BigDecimal(2));
        List<AbstractProcessor> processors = new ArrayList<>();
        AbstractProcessor bigDecimalConvertProcessor = new BigDecimalConvertProcessor();
        AbstractProcessor stringConvertProcessor = new StringConvertProcessor();
        processors.add(bigDecimalConvertProcessor);
        processors.add(stringConvertProcessor);
        new FieldConvertUtil(processors,car).convert();
        System.out.println(car.getName());

    }
}
