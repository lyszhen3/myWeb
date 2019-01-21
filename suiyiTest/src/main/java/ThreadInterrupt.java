import java.util.concurrent.TimeUnit;

/**
 * Created by lys on 2019/1/16.
 * interrupt 标志为中断,如果线程在睡眠中 则设置中断标志位false
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ThreadInterrupt {

	public static void main(String[] args) {

		Thread t = new Thread(){
			@Override
			public void run() {
				int num = 0;
				while(true){
					if(isInterrupted()) {
						System.out.println("中断");
						break;
					}
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					num++;
					System.out.println(num);
				}
			}
		};
		t.start();
		try {
			TimeUnit.SECONDS.sleep(3L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.interrupt();

	}
}
