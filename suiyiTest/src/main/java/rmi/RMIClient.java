package rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by lys on 5/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class RMIClient {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		//1099 是server 设定好的端口
		Registry registry = LocateRegistry.getRegistry(1099);
		RemoteHelloWord hello = (RemoteHelloWord) registry.lookup("helloworld");
		System.out.println(hello.sayHello());
	}
}
