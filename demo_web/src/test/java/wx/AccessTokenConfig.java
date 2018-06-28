package wx;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lys on 6/5/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class AccessTokenConfig {
	public final static String url = "https://api.weixin.qq.com/cgi-bin/token";
	public final static String appid ="wxffc62f1eb933fa53";
	public final static String secret="8d25babb851a654c5685b672a65c48fc";
	public final static String grant_type="client_credential";
	@Test
	public void postTowx() throws IOException {

		System.out.println("新版本");
		System.out.println("提交1");
		System.out.println("提交233");
		List<NameValuePair> paramList = new ArrayList<>();
		paramList.add(new BasicNameValuePair("grant_type",grant_type));
		paramList.add(new BasicNameValuePair("appid",appid));
		paramList.add(new BasicNameValuePair("secret",secret));
		HttpClient httpClient =  HttpClientBuilder.create().build();

        String uri = url;
	    String str = EntityUtils.toString(new UrlEncodedFormEntity(paramList,
                    Charset.forName("utf-8")));

		HttpGet httpGet = new HttpGet(uri+"?"+str);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpGet.setConfig(requestConfig);

//        StringEntity postEntity = new StringEntity(data, "UTF-8");
//        httpGet.addHeader("Content-Type", "text/xml");
//        httpGet.addHeader("User-Agent", "wxpay sdk java v1.0 " + config.getMchID());  // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}
}
