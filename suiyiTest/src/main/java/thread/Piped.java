package thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by lys on 2018/7/25.
 * 管道输入/输出流
 * 主要用鱼线程之间数据传输，传输媒介为内存
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Piped {
	public static void main(String[] args) throws IOException {
		PipedWriter out = new PipedWriter();
		PipedReader in = new PipedReader();
		//将输出流和输入流链接，否则在使用时会抛出IOException
		out.connect(in);
		Thread printThread = new Thread(new Print(in),"PrintThread");
		printThread.start();
		int recieve = 0;
		try {
			while((recieve=System.in.read())!=-1){
				out.write(recieve);
			}
		} finally {
			out.close();
		}


	}

	static class Print implements Runnable{
		private PipedReader in;
		public Print(PipedReader in){
			this.in = in;
		}
		@Override
		public void run() {
			int receive = 0;
			try {
				while((receive = in.read())!=-1){
					System.out.print((char)receive);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
