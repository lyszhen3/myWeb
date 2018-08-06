package thread.volatileintroduce;

/**
 * Created by lys on 2018/8/2.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class VolatileFactory {


	private static final class FactoryBean{
		private static Bar bar = new Bar();
	}
	public static Bar getInstance(){
		return FactoryBean.bar;
	}
}
