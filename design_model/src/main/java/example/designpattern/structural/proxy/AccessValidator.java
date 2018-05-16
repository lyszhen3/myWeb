package example.designpattern.structural.proxy;

/**
 * Created by lys on 5/8/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class AccessValidator {

	public boolean validate(String userId){
		System.out.printf("在数据库中验证用户:%s是否合法",userId);
		if(userId.equals("杨过")){
			System.out.println(userId+"登录成功");
			return true;
		}else{
			System.out.println(userId+"登录失败");
			return false;
		}
	}
}
