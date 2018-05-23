package example.designpattern.behavioral.templatemethod;

/**
 * Created by lys on 5/23/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public abstract class Account {
	public boolean validate(String account,String password){
		System.out.printf("账号:%s",account);
		System.out.printf("密码:%s",password);
		//模拟登录
		if(account.equals("张无忌")&&password.equals("123456")){
			return true;
		}else{
			return false;
		}
	}
	//基本方法-抽象方法
	public abstract void calculateInterest();
	//基本方法-具体方法
	public void display(){
		System.out.println("显示利息");
	}

	public void handle(String account,String password){

		if(!validate(account,password)){
			System.out.println("账号或密码错误");
			return;
		}
		calculateInterest();
		display();
	}
}
