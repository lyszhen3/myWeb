package io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by lys on 2017-08-27.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class DirList {
    public static void main(String[] args) {
        File file = new File("D:\\同步资源\\compute\\java");
        String[] list ;
//        file.list();
        list = file.list(new DirFilter(".*\\.mobi"));
        assert list != null;
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String s : list) {
            System.out.println(s);
        }
    }


}

class DirFilter implements FilenameFilter {
    private Pattern pattern;

    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }

    public DirFilter(String regex) {
        pattern = Pattern.compile(regex);
    }
}
