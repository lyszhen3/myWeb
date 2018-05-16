package example.designpattern.behavioral.chainofresponsibility;

//主任类：具体处理者
class Director extends Approver {
	public Director(String name) {
		super(name);
	}

	//具体请求处理方法
	public void processRequest(PurchaseRequest request) {
		if (request.getAmount() < 50000) {
			System.out.println("主任" + this.name + "审批采购单：" + request.getAmount());
		} else {
			this.successor.processRequest(request);
//转发请求
		}
	}
}