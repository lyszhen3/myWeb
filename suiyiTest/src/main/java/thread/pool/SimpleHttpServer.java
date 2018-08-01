package thread.pool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lys on 2018/7/26.
 * web 模拟
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SimpleHttpServer {
	//处理httpRequest的线程池
	static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);
	//simpleHttpServer的根路径
	static String basePath;
	static ServerSocket serverSocket;

	//服务监听端口
	static int port = 8080;

	public static void setPort(int port) {
		if (port > 0) {
			SimpleHttpServer.port = port;
		}
	}

	public static void setBasePath(String basePath) {
		if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
			SimpleHttpServer.basePath = basePath;
		}
	}

	//启动SimpleHttpServer
	public static void start() throws Exception {
		serverSocket = new ServerSocket(port);
		Socket socket = null;
		while ((socket = serverSocket.accept()) != null) {
			//接收一个客户端socket 生成一个httpRequestHandler,放入线程池执行
			threadPool.excute(new HttpRequestHandler(socket));

		}
		serverSocket.close();
	}


	static class HttpRequestHandler implements Runnable {
		private Socket socket;

		public HttpRequestHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String line = null;
			BufferedReader br = null;
			BufferedReader reader = null;
			PrintWriter out = null;
			InputStream in = null;

			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String header = reader.readLine();
				System.out.println(header);
				//由相对路径计算出绝对路径
				String filePath = basePath + header.split(" ")[1];

				out = new PrintWriter(socket.getOutputStream());
				//如果请求资源的后缀为jpg活着ico 则读取到资源并输出
				if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
					in = new FileInputStream(filePath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i = 0;
					while ((i = in.read()) != -1) {
						baos.write(i);
					}
					byte[] array = baos.toByteArray();
					System.out.println("图片长度"+array.length);
					out.println("HTTP/1.1 200 OK");
					out.println("Server:Molly");
					out.println("content-type:image/jpeg");
					out.println("content-length:" + array.length);
					out.println("");
					socket.getOutputStream().write(array,0,array.length);
				} else {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
					out = new PrintWriter(socket.getOutputStream());
					out.println("HTTP/1.1 200 OK");
					out.println("Server:Molly");
					out.println("Content-Type:text/html;charset=UTF-8");
					out.println("");
					while ((line = br.readLine()) != null) {
						out.println(line);
					}
				}
				out.flush();

			} catch (IOException e) {
				out.println("HTTP/1.1 500");
				out.println("");
				out.flush();
				e.printStackTrace();
			} finally {
//				close(br,in,reader,out,socket);
			}
		}

		private void close(Closeable...closeables) {
			if(closeables!=null){
				for (Closeable closeable : closeables) {
					try {
						closeable.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		setBasePath("D:\\mywork\\myWeb\\suiyiTest\\src\\main\\java\\thread\\pool");
		setPort(8080);
		start();
	}
}
