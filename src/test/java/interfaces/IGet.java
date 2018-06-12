package interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public interface IGet {
	public boolean setHeader(String header, String value);
		
	public int getResponseCode();
	
	public String getResponseBody();
	
	public void useDefaultHeaders(); 
	
	public String getResponseHeader(String header);
	
	public void createGetHTTP(String url, String params); 
	

	public String executeGetHTTP(); 
}
