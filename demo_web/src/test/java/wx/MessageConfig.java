package wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
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
public class MessageConfig {
	public final static String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
	public final static String appid = "wxffc62f1eb933fa53";
	public final static String secret = "8d25babb851a654c5685b672a65c48fc";
	public final static String openId = "onCf10HHfG7IlZaxYZ4dZCJsSWqI";

	@Test
	public void testPost() throws IOException {
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("access_token","10_os6AvXhWe4rrXRFwCI-hkgkuepkSFq65clGseAYTCHCzXCwIeYHDQAvOogDts0T0Vyz9vGIExRS84PWJHi6W50VtUiKfXniq9Ht0GfOaoc9vNsKuUI_VRvuZex7irPxFGfE1rFbmY85JImzUBQTcAJATIL"));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("keyword1", JSON.parse("{'value':'13243234'}"));
		jsonObject.put("keyword2", JSON.parse("{'value':'20080808'}"));
		jsonObject.put("keyword3", JSON.parse("{'value':'张站长'}"));
		jsonObject.put("keyword4", JSON.parse("{'value':'作品标题'}"));
		String s = JSON.toJSONString(jsonObject, SerializerFeature.UseSingleQuotes);
		System.out.println(s);
		params.add(new BasicNameValuePair("appid", appid));
		params.add(new BasicNameValuePair("secret", secret));
		params.add(new BasicNameValuePair("touser", openId));
		params.add(new BasicNameValuePair("data",""));
		params.add(new BasicNameValuePair("form_id","173927911sjflsj"));
		//预约模板id
		params.add(new BasicNameValuePair("template_id", "WiqlpN6yRs74aavbAIWJyjfmLtVT5aps5jlMInJKlaA"));
		HttpClient httpClient = HttpClientBuilder.create().build();

		HttpPost httpPost = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		httpPost.setConfig(requestConfig);
		httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
		httpPost.setHeader("content-type","application/json");
		HttpResponse execute = httpClient.execute(httpPost);
		HttpEntity httpEntity = execute.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	public static void main(String[] args) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("天哪路", JSON.parse("{\"V\":\"TT\"}"));
		System.out.println(JSON.toJSONString(jsonObject,SerializerFeature.UseSingleQuotes));
	}
	@Test
	public void testHttpEntitry() throws IOException {

		HttpGet httpGet = new HttpGet("https://www.baidu.com");
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		httpGet.setConfig(requestConfig);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse execute = httpClient.execute(httpGet);

		httpGet.addHeader("Content-Type", "application/json;charset=utf-8");
		HttpEntity entity = execute.getEntity();
		HttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
		String s = EntityUtils.toString(bufferedHttpEntity);
		System.out.println("----"+bufferedHttpEntity.getContentType()+"-----");
		System.out.println(s);
		System.out.println(EntityUtils.toString(bufferedHttpEntity,"utf-8"));


	}
}
