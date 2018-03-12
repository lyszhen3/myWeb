import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * Created by lys on 2/28/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class CcA  extends Cc{
    @Override
    void pp() {
        name = "卧槽";
    }

    public static void main(String[] args) {

        Object o = new CcA();
        Cc cc = (Cc)o;
        cc.introMy();
    }
}
