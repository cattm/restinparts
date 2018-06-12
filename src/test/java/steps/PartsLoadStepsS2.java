package steps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import commons.LoadProperties;
import commons.PartsLoadData;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import http.Post;
import interfaces.IPost;
import utils.ErrorCollector;
import utils.JsonUtils;

public class PartsLoadStepsS2 extends StepBase {
	static Logger log = Logger.getLogger(PartsLoadStepsS2.class.getName());
	
	private IPost post;
	
	private HttpPost postRequest = null;
	private String bodyText = "";
	private String jsonToLoad = "";
	private boolean setHeaders = true;
	private String contentHdr = "application/json";
	private String acceptHdr = "application/json";
	

	@Before("@plS2")
	public void setUp(Scenario scenario) throws Exception {
		log.info("running");
		super.setUp(scenario);
		 post = new Post();
	}
	
	@After("@plS2")
	public void tearDown() {
		log.info("teardown");
		super.tearDown();
	}
	
	public String selectPartsJsonString(String data) {
		 // partsload_api_scene_1_default
		log.info(LoadProperties.JSON_LOAD + data + ".json");
		return JsonUtils.jsonAsStringFromFile(LoadProperties.JSON_LOAD + data + ".json");

	}
	
	public String selectErrorString(String key) {
		if (key.contentEquals("415_MSG_4")) { return PartsLoadData.ERROR_4_TEXT;}
		else if (key.contentEquals("400_MSG_1")) { return PartsLoadData.ERROR_1_TEXT;}
		else if (key.contentEquals("400_MSG_2")) { return PartsLoadData.ERROR_2_TEXT;}
		else if (key.contentEquals("400_MSG_7")) { return PartsLoadData.ERROR_7_TEXT;}
		else if (key.contentEquals("400_MSG_8")) { return PartsLoadData.ERROR_8_TEXT;}
		else if (key.contentEquals("406_MSG_3")) { return PartsLoadData.ERROR_3_TEXT;}
		else if (key.contentEquals("415_MSG_5")) { return PartsLoadData.ERROR_5_TEXT;}
	
		else return "";
	}
	
	public int selectErrorCode(String key) {
		if (key.contentEquals("415_MSG_4")) { return PartsLoadData.RESP_ERROR_4;}
		else if (key.contentEquals("400_MSG_1")) { return PartsLoadData.RESP_ERROR_1;}
		else if (key.contentEquals("400_MSG_2")) { return PartsLoadData.RESP_ERROR_1;}
		else if (key.contentEquals("400_MSG_7")) { return PartsLoadData.RESP_ERROR_1;}
		else if (key.contentEquals("400_MSG_8")) { return PartsLoadData.RESP_ERROR_1;}
		else if (key.contentEquals("406_MSG_3")) { return PartsLoadData.RESP_ERROR_3;}
		else if (key.contentEquals("415_MSG_5")) { return PartsLoadData.RESP_ERROR_4;}
		
		else return PartsLoadData.RESP_SUCCESS;
	}
	
	@When("^I set the Post Request ([^\"]*) to ([^\"]*)$")
	public void setPostHeaderValue(String header, String value) throws Throwable {
	    if (header.contentEquals("Content-Type")) {contentHdr = value;}
	    else if (header.contentEquals("Accept")) {acceptHdr = value;}
	    else if (header.contentEquals("none")) { setHeaders = false; }
	}
	
	@And("^I send invalid configurable ([^\"]*) items via the API$")
	public void sendInvalidConfigurablePartViaAPI(String partload) throws Throwable {
		log.info("Send Invalid Configurable Parts Message");
		jsonToLoad = selectPartsJsonString(partload);
	
		post.createPostHTTP(LoadProperties.PARTS_INGRESS_URL + PartsLoadData.OFFSET, jsonToLoad);
		postRequest = post.getPostRequestObject();
		if (setHeaders) {
			post.setHeader("content-type", contentHdr);
			post.setHeader("accept", acceptHdr);
		} 
		bodyText = post.executePost(postRequest);
	}
	


	@Then("^I will recieve an appropriate ([^\"]*) message$")
	public void receiveAppropriateMessage(String checkmessage) throws Throwable {
		log.info("Receive Appropriate Message");
		log.info("return code was " + post.getResponseCode());
	    log.info("the body was " + post.getResponseBody());
	    // add asserts for code and body
	    String checkerrormsg = selectErrorString(checkmessage);
	    int checkcode = selectErrorCode(checkmessage);
	    ErrorCollector.verifyEquals(post.getResponseCode(), checkcode);
	    JSONObject start = new JSONObject(checkerrormsg);
	    JSONObject finish = new JSONObject(post.getResponseBody());
	    boolean same = JsonUtils.areEqual((Object) start, (Object) finish);
	    ErrorCollector.verifyTrue(same);
	}
	
	
}
