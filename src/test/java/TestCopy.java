import java.io.*;

/**
 * Created by pc on 2017-02-20.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class TestCopy {


    //作为复制并不靠谱
    public void copy(String startPath,String endPath){

        File file=new File(startPath);
        File targetFile=new File(endPath);

        OutputStreamWriter  out=null;
        BufferedReader reader=null;
        try {
            out=new OutputStreamWriter(new FileOutputStream(targetFile));
             reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));//编码会导致中文乱码 ，看来这个只适合用来读中文字符
            String temp=null;
            while((temp=reader.readLine())!=null){
                out.write(temp);

            }
            reader.close();
            out.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }


    }

    public void copy2(String startPath,String endPath){
        //考虑用字节流来复制 果然还是字节流来的靠谱
        byte[] b=new byte[1024];
        File file=new File(startPath);
        File targetFile=new File(endPath);

        OutputStream  out=null;
        InputStream stream=null;
        int x=0;
        try {
            stream=new FileInputStream(file);
            out=new FileOutputStream(targetFile);
            while((x=stream.read(b))!=-1){
                out.write(b,0,x);
            }
            stream.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        String start="D:\\apache-maven-3.0.5.rar";
        String end="D:\\apache-maven-3.0.52.rar";

        TestCopy copy=new TestCopy();
        // copy.copy(start,end);
        copy.copy2(start,end);

    }

}
