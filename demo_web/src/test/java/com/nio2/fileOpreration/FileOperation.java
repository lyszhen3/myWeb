package com.nio2.fileOpreration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pc on 2017-10-19.
 *关于使用nio2的一些file操作
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class FileOperation {
    private final static Path path = Paths.get("src/test","resources");
    private final static Logger log = Logger.getLogger(FileOperation.class.getName());
    private boolean isAccessable(Path p){

      return  Files.isWritable(p)&&Files.isExecutable(p)&&Files.isReadable(p)
                &&Files.isRegularFile(p, LinkOption.NOFOLLOW_LINKS);
    }

    private void dofilterAndPrintPath(Predicate<Path> pred) throws IOException {
        Files.newDirectoryStream(path).forEach(p->{
            if(pred.test(p)){
                System.out.println(p.getFileName());
            }
        });
    }

    private void precidcateWithLambda() throws IOException {

        Predicate<Path> noFilter = p->true;
        log.info("无差别输出");
        dofilterAndPrintPath(noFilter);

        Predicate<Path> dirFilter = p->Files.isDirectory(p,LinkOption.NOFOLLOW_LINKS);
        System.out.println("输出目录");
        dofilterAndPrintPath(dirFilter);

        Predicate<Path> selfFilter = p -> {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(p, Charset.forName("UTF-8"));
                String line = "";
                while((line = bufferedReader.readLine())!=null){
                    if(line.contains("江泽民"))
                        return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        };
        log.info("作死输出");
        dofilterAndPrintPath(selfFilter);

        dofilterAndPrintPath(this::isAccessable);

    }

    public static void main(String[] args) throws IOException {
        FileOperation fileOperation = new FileOperation();
        fileOperation.precidcateWithLambda();
    }



}
