import org.apache.commons.mail.HtmlEmail;

import java.io.File;
import java.util.Objects;

/**
 * Created by lys on 2018/9/6.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestClass {
	private int m;

	public void testTree() {

	}

	private static void listAll(int depth, File file) {
		printName(depth, file.getName());
		if (file.isDirectory()) {
			for (File listFile : Objects.requireNonNull(file.listFiles())) {
				listAll(depth + 1, listFile);
			}
		}
	}

	private static void printName(int depth, String name) {
		StringBuilder path = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			path.append("  ");
		}
		System.out.println(path + name);
	}

	public static void main(String[] args) {
//		listAll(0, new File("D:\\storage"));
		sendEmail("947206869@qq.com", "111");
		System.out.println("发送成功");
	}


	//	邮箱验证码
	public static boolean sendEmail(String emailaddress, String code) {
		try {
			HtmlEmail email = new HtmlEmail();//不用更改
			email.setHostName("smtp.126.com");//需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// 收件地址

			email.setFrom("lyszhen3@126.com", "aa");//此处填邮箱地址和用户名,用户名可以任意填写
             //此处填写邮箱地址和客户端授权码
			email.setAuthentication("lyszhen3@126.com", "***");

			email.setSubject("孙大大通讯");//此处填写邮件名，邮件名可任意填写
			email.setMsg("尊敬的用户您好,您本次注册的验证码是:" + code);//此处填写邮件内容

			email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
