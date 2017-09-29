package com.designPattern.decorate;

/**
 * Created by pc on 2017-07-19.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Client {

    public static void main(String[] args) {
        Compent win,box;

        win = new Window();

        box = new AddBoxDecorator(win);

        Compent bb = new BlackBordDecorator(box);

        bb.display();

    }

}
