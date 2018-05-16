package example.designpattern.behavioral.chainofresponsibility;

class Congress extends Approver {
	public Congress(String name) {
		super(name);
	}
	//具体请求处理方法

	public void processRequest(PurchaseRequest request) {
		System.out.println("召开董事会审批采购单：" + request.getNumber() + "，金额:"+request.getAmount());
	}

}