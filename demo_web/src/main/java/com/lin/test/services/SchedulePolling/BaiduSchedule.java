package com.lin.test.services.SchedulePolling;

import com.alibaba.fastjson.JSONObject;
import com.lin.data.beans.Account;
import com.lin.Test.services.TestService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lys on 6/15/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
public class BaiduSchedule implements SchedulePolling {
	private  AtomicInteger c = new AtomicInteger(0);

	private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
			new BasicThreadFactory.Builder().namingPattern("vedio-schedule-pool-%d").daemon(true).build());

	private TestService testService;

	@Autowired
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	@Override
	public void excute() {
		executorService.scheduleWithFixedDelay(new Task(), 10, 10, TimeUnit.SECONDS);
	}


	public JSONObject vedioStatus() throws IOException {

		HttpGet get = new HttpGet("http://www.baidu.com");
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		get.setConfig(requestConfig);
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
		return new JSONObject();
	}

	class Task implements Runnable {

		@Override
		public void run() {
			if (c.addAndGet(1)> 10) {
				executorService.shutdown();
				return;
			}
			System.out.println("---开始----" + c);
			if(c.get()==9){
				List<Account> accounts = testService.selList();
				System.out.println(accounts.get(0).getEmail());
			}
			JSONObject jsonObject = null;
			try {
				jsonObject = vedioStatus();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
