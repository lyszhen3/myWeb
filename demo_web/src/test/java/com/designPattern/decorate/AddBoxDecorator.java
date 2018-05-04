package com.designPattern.decorate;

/**
 * Created by pc on 2017-07-19.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class AddBoxDecorator extends CompentDecorator {

	public AddBoxDecorator(Compent compent) {
		super(compent);
	}

	@Override
	public void display() {
		super.display();
		setBox();
	}

	public void setBox() {
		System.out.println("加框框");
	}

}
