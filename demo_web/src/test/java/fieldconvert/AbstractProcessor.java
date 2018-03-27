package fieldconvert;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lys on 3/13/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public abstract class AbstractProcessor<A extends Annotation,B> {
    private void init(){
        Type genType = this.getClass().getGenericSuperclass();//得到泛型父类
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.annotation = (Class<A>) params[0];
        this.fieldType = (Class<B>)params[1];

    }
    public AbstractProcessor(){
          init();
    }

    protected Class<A> annotation;

    Class<B> fieldType;

    public abstract B convert( B oriValue,Annotation annotation);

    public static void main(String[] args) {

    }
}
