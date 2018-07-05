package jvmsecurity;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by lys on 7/5/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SecurityTests {

	public static void main(String[] args) {
		if (System.getSecurityManager() != null) {
			//如果安全管理器不为空，则越过权限限制执行下面得代码
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {

				return null;
			});

		}
	}
}
