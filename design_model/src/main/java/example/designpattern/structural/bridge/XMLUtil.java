package example.designpattern.structural.bridge;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;

public class XMLUtil {
    //该方法用于从XML配置文件中提取具体类类名，并返回一个实例对象
    public static Object getBean(String args) {
        try {
            //创建DOM文档对象  
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;
            URL resource = XMLUtil.class.getResource("");
            doc = builder.parse(new File(resource.getPath().replace("target/classes/", "src/main/java/") + "config.xml"));

            //获取包含类名的文本节点  
            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = null;
            if (args.equals("image")) {
                classNode = nl.item(0).getFirstChild();
            } else if (args.equals("os")) {
                classNode = nl.item(1).getFirstChild();
            }
            String cName = classNode.getNodeValue();

            //通过类名生成实例对象并将其返回
            String packageName = XMLUtil.class.getPackage().getName();
            Class c = Class.forName(packageName + "." + cName);
            Object obj = c.newInstance();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}