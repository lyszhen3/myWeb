package example.designpattern.behavioral.state.switchdemo;

abstract class State {
    public abstract void on(Switch s);  
    public abstract void off(Switch s);  
}