import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * idea plugin iedis 破解
 * 将生成的文件更新到jar包
 * jar -uvf [jarpath] [classpath]
 */
public class CrackRedisIdeaPlugin331 {
    public ClassPool pool = ClassPool.getDefault();

    @Test
    public void test() throws NotFoundException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        pool.insertClassPath("C:\\Users\\pc\\Desktop\\iedis-2.43.jar");
        CtClass L = pool.get("com.seventh7.widget.iedis.L");
        CtClass p = pool.get("com.seventh7.widget.iedis.a.p");

        try {

            CtMethod ct = L.getDeclaredMethod("a", new CtClass[]{pool.get("java.lang.String")});
            ct.setBody("{ return \"{" +
                    "\\\"trailing\\\": true," +
                    "\\\"daysLeft\\\": 9," +
                    "\\\"popup\\\": true," +
                    "\\\"activated\\\": false" +
                    "}\"; }");


            ct = p.getDeclaredMethod("f", new CtClass[]{});
            ct.setBody("{ " +
                    "            java.util.Map d = this.d();\n" +
                    "\n" +
                    "            // trail\n" +
                    "            boolean booleanValue = false;\n" +
                    "\n" +
                    "            // daysLeft\n" +
                    "            int b2 = 3650;\n" +
                    "\n" +
                    "            // popup\n" +
                    "            // 如果不需要弹窗提示剩余天数，改为false\n" +
                    "            boolean booleanValue2 = false;\n" +
                    "\n" +
                    "            this.a(b2, booleanValue2);\n" +
                    "            return false;" +
                    "}");

            L.writeFile("C:\\Users\\pc\\Desktop\\popo\\");
            p.writeFile("C:\\Users\\pc\\Desktop\\popo\\");
        } catch (Exception e) {
            System.out.println("111");
            e.printStackTrace();
        }
    }
}