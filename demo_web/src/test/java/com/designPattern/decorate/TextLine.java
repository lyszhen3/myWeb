package com.designPattern.decorate;

/**
 * Created by pc on 2017-07-19.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class TextLine extends Compent{
    @Override
    public void display() {
        super.display();
        System.out.println("文字装修");
    }


}
