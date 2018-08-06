package thread.volatileintroduce;

import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2018/8/2.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Bar {
	private String name;
	private String id;

	private volatile Foo foo;
	private long s;
	private volatile boolean v = false;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public  void setFoo(){
		if(foo == null){
			//写
			foo = new Foo();
		}
		System.out.println(foo);

	}

	public long getS() {
		return s;
	}

	public void setS(long s) {
		this.s = s;
	}

	public void voliate()   {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!v){
			v = true;
			System.err.println("设置未正确:"+false);
		}else{
			System.out.print("11");
		}
	}
}
