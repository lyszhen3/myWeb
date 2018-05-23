package example.designpattern.behavioral.templatemethod;

/**
 * Created by lys on 5/23/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Client {
	public static void main(String[] args) {
		Account account;
		account= (Account)XMLUtil.getBean();
		account.handle("张无忌","123456");
	}
}
