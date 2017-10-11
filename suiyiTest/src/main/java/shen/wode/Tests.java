package shen.wode;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by pc on 2017-10-11.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class Tests {
    public static void main(String[] args) throws IOException {
        //D:/lin/data/1.txt
        String path = "D:/lin/data/1.txt";
        File file = new File(path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

    }
}
