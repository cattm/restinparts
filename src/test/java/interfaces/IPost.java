package interfaces;

import org.apache.http.client.methods.HttpPost;

public interface IPost {


	public HttpPost createPostHTTP(String url, String body);
	public String executePost(HttpPost thepost);
	public HttpPost getPostRequestObject();
	public int getResponseCode();
	public String getResponseBody();
	public void useDefaultHeaders();
	public void setHeader(String key, String value);
}
