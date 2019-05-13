package rmi.stubSketeton;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Person_Stub implements Person {
	private Socket socket;

	public Person_Stub() throws Throwable {
		// connect to skeleton
		socket = new Socket("localhost", 9000);

	}

    @Override
    public int getAge() throws Throwable {
        // pass method name to skeleton
        ObjectOutputStream outStream =
            new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("age");
        outStream.flush();
        ObjectInputStream inStream =
            new ObjectInputStream(socket.getInputStream());
        return inStream.readInt();
    }

    @Override
    public String getName() throws Throwable {
        // pass method name to skeleton
        ObjectOutputStream outStream =
            new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject("name");
        outStream.flush();
        ObjectInputStream inStream =
            new ObjectInputStream(socket.getInputStream());
        return (String) inStream.readObject();
    }
}     