package intellijidea;

/**
 * Created by lys on 6/23/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Deprecation {

	public void deprecationWarnings() {
		MyClass myClass = new MyClass();
		//敲入alt+enter 即可替换
		myClass.deprecatedMethod();
	}

	/**
	 *
	 * @param id
	 * id
	 * @param name
	 * name
	 */
	private void deprecationParam(@Deprecated String id, String name){
		System.out.println(id+":"+name);

	}

	public static void main(String[] args) {
		Deprecation deprecated = new Deprecation();
		deprecated.deprecationParam("12","fuck");
	}

	public class MyClass {

		/**
		 * @deprecated use {@link MyClass#replacementMethod()} instead.
		 */
		void deprecatedMethod() {

		}

		void replacementMethod() {

		}
	}

}
