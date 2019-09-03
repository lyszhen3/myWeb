/**
 * @author LinYuanSheng
 * @date 05/29/2019
 */
public interface FunctionIn {

    String test(String arg);
    default String test(String arg1, Integer arg2){
      return null;
    };
}
