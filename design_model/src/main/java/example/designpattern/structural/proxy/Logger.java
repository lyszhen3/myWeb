package example.designpattern.structural.proxy;

/**
 * Created by lys on 5/8/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Logger {
	public void log(String userId){
		System.out.printf("更新数据库,用户%s查询次数+1",userId);
	}

	public static void main(String[] args) {
	}
}
