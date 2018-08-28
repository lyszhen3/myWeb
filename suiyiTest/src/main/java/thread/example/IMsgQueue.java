package thread.example;

/**
 * Created by lys on 2018/8/19.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public interface IMsgQueue {

	void put(Message message);

	Message take();

}
