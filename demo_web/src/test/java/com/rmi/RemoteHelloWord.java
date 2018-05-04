package com.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by lys on 5/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public interface RemoteHelloWord extends Remote {
	String sayHello() throws RemoteException;
}
