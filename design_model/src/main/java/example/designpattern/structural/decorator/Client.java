package example.designpattern.structural.decorator;

class Client {
	public static void main(String args[]) {
		Component component, componentSB,componentBB; //使用抽象构件定义
		component = new Window();
		//定义具体构件
		componentSB = new ScrollBarDecorator(component);
		componentBB = new BlackBorderDecorator(componentSB);

		//定义装饰后的构
		componentBB.display();
	}
}