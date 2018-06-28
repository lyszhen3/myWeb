package shen.fuzi;

/**
 * Created by lys on 6/27/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public abstract class AbstarctClass {


	public void sout(){
		System.out.println("我是老大");
	}

	void diaosout(){
		this.sout();
	}
}
