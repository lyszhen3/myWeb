package example.designpattern.behavioral.observer.jdkobserver;

import java.util.Observable;

/**
 * Created by lys on 5/22/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class BannerControl extends Observable {
	private String name;

	public void delete(){
		System.out.println("数据更新");
		setChanged();
		notifyObservers(Curd.DELETE);
	}

	public enum Curd{
		CREATE,UPDATE,RETRIEVE,DELETE
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

