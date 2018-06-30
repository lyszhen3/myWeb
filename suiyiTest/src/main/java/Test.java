import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by pc on 2017-10-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Test {
    /****/
    @org.junit.Test
    public void test(){
      Test test =null;
        System.out.println("上一个test"+test);
      if(test == null && (test = new Test())==null){
          System.out.println("空空");
      }
        System.out.println("下一个test"+test);
    }


    public void xx(Inin in){
        if(in instanceof IninImpl3){
            System.out.println("impl1");
        }else{
            System.out.println("impl2");
        }
    }

    public static void main(String[] args) throws IOException {
    }

  public static void deleteFile(File file) throws IOException {
      ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

}


}
