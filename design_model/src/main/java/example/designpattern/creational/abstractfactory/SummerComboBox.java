package example.designpattern.creational.abstractfactory;

class SummerComboBox implements ComboBox {
    @Override
    public void display() {
        System.out.println("显示蓝色边框组合框。");  
    }     
}