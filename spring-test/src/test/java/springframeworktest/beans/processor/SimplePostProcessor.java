package springframeworktest.beans.processor;

/**
 * Created by lys on 7/8/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SimplePostProcessor {
	private String connectionString;
	private String password;
	private String userName;

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "SimplePostProcessor{" +
				"connectionString='" + connectionString + '\'' +
				", password='" + password + '\'' +
				", userName='" + userName + '\'' +
				'}';
	}
}
