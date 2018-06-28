import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import mvctest.Car;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by pc on 2017/9/25.
 * 随便测试类
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @description
 * @since 3.0.0-SNAPSHOT
 */
public class STest {
    @Test
    public  void main() throws IOException {
       Settings settings = Settings.builder()

              .put("client.transport.sniff", true)
               .build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();

        System.out.println(response.toString());
        client.close();
    }
    @Test
    public void testMoreThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i);
                }
            }
        });
        thread.start();
        try {
            paBaidu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }

    @Test
    public void paBaidu() throws IOException {
    	Myclass myclass = new Myclass();
    	myclass.deprecateMethod();

    }
    public class Myclass{

        /**
         * @deprecated use {@link Myclass#replacementMethod()} instead.
         */
        void deprecateMethod(){

        }
        void replacementMethod(){

        }
    }

}
