package com.adeveloperdiry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.google.gson.JsonParser;

public class HttpUtils {

	private String server = "http://localhost:3000";
	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;

	public HttpUtils() {
		this.rest = new RestTemplate();
		this.headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
	}

	private static final String AUTHENDPOINT = "http://c-auth-qa4.copart.com"; 
	private static final String username = "opsportusr@copart.com"; 
	private static final String password = "Nhslt9aike!2";
	private static final String grantType = "password";
	private static final String path = "employee/oauth/token";
	private static final String authorization = "Basic dGVzdGNsaWVudDp0ZXN0c2VjcmV0";

	public String getToken(String username, String password, String Url) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Authorization", authorization);
		HttpEntity<?> httpEntity = new HttpEntity<>(requestHeaders);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Url).path(path).queryParam("username", username)
				.queryParam("password", password).queryParam("grant_type", grantType);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST,
				httpEntity, String.class);
		String responseBody = response.getBody();
		JsonParser parser = new JsonParser();
		String token = parser.parse(responseBody).getAsJsonObject().get("access_token").getAsString();
		return token;
	}

	// Headers, Http entity, Builder, 
	public String get(String url, String uri, String username, String password) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public String post(String url, String uri, String username, String password, String json) {
		String token = getToken(username, password, url);
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	public void put(String url, String uri, String username, String password, String json) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		this.setStatus(responseEntity.getStatusCode());
	}

	public void delete(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.DELETE, requestEntity,
				String.class);
		this.setStatus(responseEntity.getStatusCode());
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}