package example.designpattern.behavioral.observer.jdkobserver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by lys on 5/22/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Banner implements Observer {
	private String name;
	private BannerControl bannerControl;
	public Banner(String name,BannerControl b){
		this.name = name;
		this.bannerControl = b;
		b.addObserver(this);
	}


	@Override
	public void update(Observable o, Object arg) {
		switch( (BannerControl.Curd)arg){
			case DELETE:{
				delete(bannerControl);
				break;
			}
			case CREATE:{
				create();
				break;
			}
			case UPDATE:{
				update();
				break;
			}
			case RETRIEVE:{
				retrieve();
				break;
			}
			default:{
				break;
			}
		}
	}
	public void delete(BannerControl b){
		System.out.println("始作俑者"+b.getName()+"::"+this.name+"删除");

	}
	public void update(){
		System.out.println(this.name+"添加");
	}
	public void retrieve(){
		System.out.println(this.name+"查看");
	}
	public void create(){
		System.out.println(this.name+"添加");
	}
}
