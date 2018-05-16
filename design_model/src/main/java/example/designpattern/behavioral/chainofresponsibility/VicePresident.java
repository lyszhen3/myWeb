package example.designpattern.behavioral.chainofresponsibility;
//副董事长类：具体处理者

class VicePresident extends Approver {
	public VicePresident(String name) {
		super(name);
	}
	//具体请求处理方法

	public void processRequest(PurchaseRequest request) {
		if (request.getAmount() < 100000) {
			System.out.println("副董事长" + this.name + "审批采购单：" + request.getAmount());
		} else {
			this.successor.processRequest(request); //转发请求
		}
	}
}