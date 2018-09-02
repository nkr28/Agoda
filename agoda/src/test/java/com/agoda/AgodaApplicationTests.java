package com.agoda;

import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.AgodaApplication;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgodaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgodaApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@SuppressWarnings("deprecation")
	@Test
	public void testResultForCity() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/agoda/city/Bangkok?sort=desc"),
				HttpMethod.GET, entity, String.class);

		String actual = response.getBody();
		actual = actual.substring(actual.indexOf("city") + 7, actual.indexOf("city") + 14);
		String expected = "Bangkok";
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void testRate() {
		try {
			int noHitsToBeTested = 20;
			int timeDurationInMills = 10000;
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(createURLWithPort("/agoda/city/Bangkok?sort=desc"));

			HttpResponse response = null;
			int expected = 200;
			Calendar calStart = Calendar.getInstance();
			for (int i = 0; i < noHitsToBeTested; i++) {
				response = client.execute(request);
				request.releaseConnection();
			}
			int actual = response.getStatusLine().getStatusCode();
			Calendar calEnd = Calendar.getInstance();
			if (calEnd.getTimeInMillis() - calStart.getTimeInMillis() < timeDurationInMills) {
				expected = 429;
			}
			Assert.assertEquals(expected, actual);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Get the response

		/*
		 * HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		 * ResponseEntity<String> response = null;
		 * restTemplate.exchange(createURLWithPort("/agoda/city/Bangkok?sort=desc"),
		 * HttpMethod.GET, entity, String.class); int noHitsToBeTested = 20; int
		 * timeDurationInMills = 10000; Calendar calStart = Calendar.getInstance(); for
		 * (int i = 0; i < noHitsToBeTested; i++) { try { response =
		 * restTemplate.exchange(createURLWithPort("/agoda/city/Bangkok?sort=desc"),
		 * HttpMethod.GET, entity, String.class); } catch (Exception e) {
		 * System.out.println("yy"); } } Calendar calEnd = Calendar.getInstance(); int
		 * actual = response.getStatusCode().value(); int expected = 200; if
		 * (calEnd.getTimeInMillis() - calStart.getTimeInMillis() > timeDurationInMills)
		 * { expected = 429; } Assert.assertEquals(expected, actual);
		 */
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
