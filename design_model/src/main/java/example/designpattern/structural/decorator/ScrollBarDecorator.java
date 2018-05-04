package example.designpattern.structural.decorator;

class ScrollBarDecorator extends ComponentDecorator {
	public ScrollBarDecorator(Component component) {
		super(component);
	}

	public void display() {
		this.setScrollBar();
		super.display();
	}

	public void setScrollBar() {
		System.out.println("为构件增加滚动条！");
	}
}