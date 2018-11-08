package springannotation;

/**
 * Created by lys on 7/5/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Foo {
	private Bar bar;

	public Foo(Bar bar){
		this.bar = bar;
	}
	public Foo(){

	}
	public Bar getBar() {
		return bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}
}
