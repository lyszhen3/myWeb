package example.designpattern.behavioral.observer.jdkobserver;

/**
 * Created by lys on 5/22/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Client {

	public static void main(String[] args) {

		BannerControl bannerControl = new BannerControl();
		bannerControl.setName("qq");
		Banner b1,b2,b3;
		b1 = new Banner("第一横幅",bannerControl);
		b2 = new Banner("第二横幅",bannerControl);
		b3 = new Banner("第三横幅",bannerControl);
		bannerControl.delete();
	}
}
