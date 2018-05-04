package example.designpattern.creational.abstractfactory;

class SummerButton implements Button {
    @Override
    public void display() {
        System.out.println("显示浅蓝色按钮。");
    }
}  