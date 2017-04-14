package test.com.wb.user;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by wangbo on 2017/4/13.
 */
public class CommonTest {
	
	@Test
	public void getUrl() throws IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpGet = new HttpGet("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255");
		
		CloseableHttpResponse response = httpClient.execute(httpGet);
		
		
		HttpEntity entity = response.getEntity();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
		String line = bufferedReader.readLine();
		while (line != null) {
			System.out.println(line);
			line = bufferedReader.readLine();
		}
		
		System.out.println("Login form get: " + response.getStatusLine());
		if (entity != null) {
			entity.consumeContent();
		}
		System.out.println("Initial set of cookies:");
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println("- " + cookies.get(i).toString());
			}
		}
		
	}
	
}
