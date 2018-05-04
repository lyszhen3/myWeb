package com.rmi.stubSketeton;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Person_Skeleton extends Thread {
	private PersonServer myServer;

	public Person_Skeleton(PersonServer server) {
		// get reference of object server
		this.myServer = server;
	}

	public void run() {
		try {
			// new socket at port 9000
			ServerSocket serverSocket = new ServerSocket(9000);
			// accept stub's request
			Socket socket = serverSocket.accept();

			while (socket != null ) {
				// get stub's request
				ObjectInputStream inStream =
						new ObjectInputStream(socket.getInputStream());
				String method = (String) inStream.readObject();
				// check method name
				if (method.equals("age")) {
					// execute object server's business method
					int age = myServer.getAge();
					ObjectOutputStream outStream =
							new ObjectOutputStream(socket.getOutputStream());
					// return result to stub
					outStream.writeInt(age);
					outStream.flush();
				}
				if (method.equals("name")) {
					// execute object server's business method
					String name = myServer.getName();
					ObjectOutputStream outStream =
							new ObjectOutputStream(socket.getOutputStream());
					// return result to stub
					outStream.writeObject(name);
					outStream.flush();
				}
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public static void main(String args[]) {
		// new object server
		PersonServer person = new PersonServer("霍去病", 34);
		Person_Skeleton skel = new Person_Skeleton(person);
		skel.start();
	}
}     