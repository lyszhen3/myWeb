package example.designpattern.behavioral.templatemethod;

/**
 * Created by lys on 5/23/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SavingAccount extends Account{
	@Override
	public void calculateInterest() {
		System.out.println("按定期计算利息");
	}
}
