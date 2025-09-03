/**
 * @date 2025-09-03
 * @author LinYuansheng
 */
public class StringTest {

    public static void main(String[] args) {
        String ii = "abc";

        int length = ii.length();
        for (int i = 0; i < length; i++) {
            String sub1 = ii.substring(0, i + 1);
            System.out.println(sub1);
        }
    
    }
}
