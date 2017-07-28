package com.designPattern.decorate;

/**
 * Created by pc on 2017-07-19.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public abstract class CompentDecorator extends Compent {
    private Compent compent;

    public CompentDecorator(Compent compent){
        this.compent = compent;
    }


    @Override
    public void display() {
        compent.display();
    }
}
