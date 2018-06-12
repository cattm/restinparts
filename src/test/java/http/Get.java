package http;



	
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import interfaces.IGet;
import steps.StepBase;

public class Get implements IGet {
	static Logger log = Logger.getLogger(Get.class.getName());
		private HttpClient httpClient = null;
		private HttpGet request = null;
		private StringEntity params = null;		
		private HttpResponse response;
		private String returnBodyText = "";
		private URI uri = null;
		private List <NameValuePair> nvp = null;

		String contentHdr = "application/json";
		String acceptHdr = "application/json"; 
	
		/*
		public void addParam (String name, String value) {
			if (nvp != null) {
				nvp = new ArrayList <NameValuePair>();			
			}
			NameValuePair nv = new BasicNameValuePair(name,value);	
			nvp.add(nv);
		}
		
		public void addParamList(ArrayList <NameValuePair> paramlist) {
			if (nvp != null) {
				nvp = new ArrayList <NameValuePair>();			
			}
			// copy list paramlist into nvp
		}
		*/
		public boolean setHeader(String header, String value){
			request.addHeader(header, value);
			return true;
		}
		
		
		public int getResponseCode() {
			return response.getStatusLine().getStatusCode();
		}
		
		public String getResponseBody() {
			return returnBodyText;
		 
		}
		
		
		public void useDefaultHeaders() {
			setHeader("content-type", contentHdr);
			setHeader("accept", acceptHdr);
		}
		
		public void createGetHTTP(String url, String params) {
			log.info(url);
		 	log.info(params);
			httpClient = HttpClientBuilder.create().build(); //Use this instead 
			request = new HttpGet(url + params);
		
		}
				
		public String getResponseHeader(String header) {
			Header myheader = response.getFirstHeader(header);
			String value = myheader.getValue();
			return value;
		}
		
		@SuppressWarnings("deprecation")
		public String executeGetHTTP() {
			try {
				response = httpClient.execute(request);
				if (response.getStatusLine().getStatusCode() != 200) {
					log.info("Status not equal 200");;
				}
				BufferedReader br;
				br = new BufferedReader(
		                 new InputStreamReader((response.getEntity().getContent())));
				String output;
				while ((output = br.readLine()) != null) {
					returnBodyText += output;
				}
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		  
		  return returnBodyText;
		}

		
}
