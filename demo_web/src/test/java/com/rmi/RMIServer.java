package com.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by lys on 5/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class RMIServer {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		RemoteHelloWord hello = new RemoteHelloWorldImpl();
		RemoteHelloWord stub = (RemoteHelloWord) UnicastRemoteObject.exportObject(hello,9999);
		LocateRegistry.createRegistry(1099);
		Registry registry = LocateRegistry.getRegistry();
		registry.bind("helloworld",stub);
		System.out.println("绑定成功！");
	}
}
