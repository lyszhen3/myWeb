package com.designPattern.decorate;

/**
 * Created by pc on 2017-07-19.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class BlackBordDecorator extends CompentDecorator {

	public BlackBordDecorator(Compent compent) {
		super(compent);
	}

	@Override
	public void display() {
		super.display();
		setBlack();
	}

	public void setBlack() {
		System.out.println("变黑吧");
	}

}
