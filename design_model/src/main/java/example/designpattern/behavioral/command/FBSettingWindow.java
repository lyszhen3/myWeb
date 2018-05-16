package example.designpattern.behavioral.command;

import java.util.ArrayList;

//功能键设置窗口类
class FBSettingWindow {
	private String title; //窗口标题
	// 定义一个ArrayList来存储所有功能键
	private ArrayList<FunctionButton> functionButtons = new ArrayList<FunctionButton>();

	public FBSettingWindow(String title) {
		this.title = title;
	}

	//显示窗口及功能键
	public void display() {
		System.out.println("显示窗口：" + this.title);
		System.out.println("显示功能键：");
		for (Object obj : functionButtons) {
			System.out.println(((FunctionButton) obj).getName());
		}
		System.out.println("------------------------------");
	}

	public void addFunctionButton(FunctionButton fb){
		this.functionButtons.add(fb);
	}
	public void removeFunctionButton(FunctionButton fb){
		this.functionButtons.remove(fb);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<FunctionButton> getFunctionButtons() {
		return functionButtons;
	}

	public void setFunctionButtons(ArrayList<FunctionButton> functionButtons) {
		this.functionButtons = functionButtons;
	}
}