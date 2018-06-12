package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import interfaces.IPost;

public class Post implements IPost {
	
	static Logger log = Logger.getLogger(Post.class.getName());
	private HttpClient httpClient = null;
	private HttpResponse response;
	private String bodyText = "";
	private HttpPost request = null;
	String contentHdr = "application/json";
	String acceptHdr = "application/json";

	
	public HttpPost createPostHTTP(String url, String body) {
		httpClient = HttpClientBuilder.create().build(); //Use this instead 
		try {
		    request = new HttpPost(url);
		    StringEntity params =new StringEntity(body);
		    request.setEntity(params);
		}catch (Exception ex) {
		    // handle exception here
			 log.info("Exception " + ex);
		}
		if (request != null) log.info("request is valid");
		return request;   
	}
	
	public HttpPost getPostRequestObject() {
		return request;
	}
	
	public String executePost(HttpPost thepost) {
		try {
			response = httpClient.execute(thepost);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			
			while ((output = br.readLine()) != null) {
				bodyText += output;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bodyText;
	}
	
	public void setHeader(String key, String value) {
		try {
			request.addHeader(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void useDefaultHeaders() {
		setHeader("content-type", contentHdr);
		setHeader("accept", acceptHdr);
	}
	
	public int getResponseCode() {
		return response.getStatusLine().getStatusCode();
	}
	
	public String getResponseBody() {
		return bodyText;
	 
	}	

}
