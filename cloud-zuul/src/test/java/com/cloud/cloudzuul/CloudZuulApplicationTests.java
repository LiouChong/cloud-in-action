package com.cloud.cloudzuul;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CloudZuulApplicationTests {

	@Autowired
	private RestTemplate restTemplate;
	@Test
	public void contextLoads() {
	}

	@Test
	public void testHandleRestInterface() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8083/v1/organizations/442adb6e-fa58-47f3-9ca2-ed1fecdfe86c");
		HttpResponse response = httpClient.execute(httpGet);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			String s = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(s);
		}
	}
	@Test
	public void testTemplateHandle() {
		ResponseEntity<String> exchange = restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}", HttpMethod.GET, null, String.class, 1);
		System.out.println(exchange.getBody());
	}


}
