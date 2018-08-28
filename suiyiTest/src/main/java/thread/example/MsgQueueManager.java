package thread.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by lys on 2018/8/19.
 * 总消息队列管理
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class MsgQueueManager implements IMsgQueue {


	/**
	 * 消息总队列
	 */
	public final BlockingQueue<Message> messageQueue;

	public MsgQueueManager() {
		this.messageQueue = new LinkedTransferQueue<>();
	}
	@Override
	public void put(Message msg){
		try {
			messageQueue.put(msg);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	@Override
	public Message take(){
		try {
			return messageQueue.take();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return null;
	}


	static class DispatchMessageTask implements Runnable{


		@Override
		public void run() {
			BlockingQueue<Message> subQueue;
			for (;;){

				//如果没有数据,则阻塞在这里
//				Message msg = MsgQueueFactory.getMessageQueue.take();

			}

		}
	}
}
