package steps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import commons.LoadProperties;
import commons.PartsAPIData;
import commons.PartsLoadData;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import http.Get;
import http.Post;
import interfaces.IGet;
import utils.ErrorCollector;
import utils.JsonUtils;

public class PartsAPIStepsS1 extends StepBase {
	
	static Logger log = Logger.getLogger(PartsAPIStepsS1.class.getName());
	
	
	private IGet httpGet = null;
	private String bodyText = "";
	private String token = "";
	private String configFilter = "";
	
	
	@Before("@ptS1")
	public void setUp(Scenario scenario) throws Exception {
		log.info("running");
		super.setUp(scenario);
		httpGet = new Get();
	}
	
	@After("@ptS1")
	public void tearDown() {
		log.info("teardown");
		super.tearDown();
	}
	

	
	String getConfigFilter(String key) {
		// this method is very crude - works but we should refactor to be more elegant
		// the BDD needs to key 2 things - the expected result and the filter to apply
		if (key.contentEquals("CFILTER_1_RECORD")) { return PartsAPIData.CFILTER_1;}
		else if (key.contentEquals("CFILTER_3_RECORD")) { return PartsAPIData.CFILTER_3;}
		else if (key.contentEquals("CFILTER_5_RECORD")) { return PartsAPIData.CFILTER_5;}
		else if (key.contentEquals("CFILTER_6_RECORD")) { return PartsAPIData.CFILTER_6;}
		else if (key.contentEquals("CFILTER_4_PAGE")) { return PartsAPIData.CFILTER_MULTI_4;}
		else if (key.contentEquals("CFILTER_16_PAGE")) { return PartsAPIData.CFILTER_MULTI_16; }
		else return PartsAPIData.CFILTER_1;
	}
	

	
	String SelectPartsJsonString(String data) {
		return JsonUtils.jsonAsStringFromFile(LoadProperties.JSON_VERIFY + data + ".json");

	}
		
	private String tidyPartUrl(String url) {
		String tidy = url.replace("|", "%7C");
		tidy = tidy.replaceFirst("/", "");	
		return tidy;
	}
	private String selectPageToget(JSONObject source,String key) {
		// page is either next/last etc or its a number (as string)
		// if its a number then we need to add &pageNumber=N to the quesry
		// note we may need to replace the "|" with %7C to avoid a URISyntaxException
		
		//log.info(key);
		//log.info("processing source " + source.toString());
		if (key.contentEquals("self")  ||
			key.contentEquals("last")  ||
			key.contentEquals("next")  ||
			key.contentEquals("prev")  ||
			key.contentEquals("first") ) {
			JSONObject tmp = JsonUtils.getLink(source, key);
			String resp = tmp.get("href").toString();
			//log.info(resp);
			return tidyPartUrl(resp);
		}
		else { // assume its a number for now
			//int page = Integer.parseInt(key);
			//return "parts?pageNumber=3&characteristics=4_PAGE%7CFOUR";
			String pagebuilt = PartsAPIData.OFFSET + "?pageNumber=" + key + "&" + PartsAPIData.PARAM + "=" + configFilter;
			return pagebuilt;
		}

	}
	
	/*
	@Given("^I have a valid ([^\"]*) that enables access to configurable parts$")
	public void iHaveValidNameEnablesAccess(String arg1) throws Throwable {
		log.info("I Have Valid Name : " + arg1);
		token = getConfigToken(arg1);
	}
    */
	
	
	@When("^I request access to configurable parts that exist with a valid ([^\"]*)$")
	public void iRequestAccessWithValidFilter(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		log.info("I request Access to : " + arg1);
		configFilter = getConfigFilter(arg1);
		httpGet.createGetHTTP(LoadProperties.PARTS_API_URL + 
				        PartsAPIData.OFFSET + "?" + 
				        PartsAPIData.PARAM + "=", 
				        configFilter);
		
		httpGet.useDefaultHeaders();
		httpGet.setHeader("Authorization", UtilitySteps.getMyToken());
		httpGet.executeGetHTTP();
		
		log.info("return code was " + httpGet.getResponseCode());
		ErrorCollector.verifyEquals(PartsLoadData.RESP_SUCCESS,httpGet.getResponseCode() );
	}


	@Then("^I am able to view and ([^\"]*) configurable parts for download successfully$")
	public void iViewAndValidateJson(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		log.info("I am able to view and validate : " + arg1);	
		log.info("the body was " + httpGet.getResponseBody());
		
		JSONObject response = new JSONObject(httpGet.getResponseBody());
		JSONObject expected = new JSONObject(SelectPartsJsonString(arg1));
		boolean same = JsonUtils.areEqual((Object) expected, (Object) response);
	    ErrorCollector.verifyTrue(same);
	}

	@Then("^I am able to select ([^\"]*) of the page options provided$")
	public void iSelectThePage(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		// we just need to add pageNumber=X as first param
		// TODO - either have a page number 1,2,3,4 or use the link!
		JSONObject response = new JSONObject(httpGet.getResponseBody());
		log.info("I am able to select page: " + arg1);
		
	
		IGet lhttpget = new Get();
		
		String newurlparams = selectPageToget(response, arg1);
		lhttpget.createGetHTTP(LoadProperties.PARTS_API_URL, newurlparams);

		lhttpget.useDefaultHeaders();
		lhttpget.setHeader("Authorization", UtilitySteps.getMyToken());
		lhttpget.executeGetHTTP();
		log.info("return code was " + lhttpget.getResponseCode());
		ErrorCollector.verifyEquals(PartsLoadData.RESP_SUCCESS,lhttpget.getResponseCode() );
		log.info("the body was " + lhttpget.getResponseBody());
		bodyText = lhttpget.getResponseBody();
		
	}
	
	@And("^I can ([^\"]*) the page loaded$")
	public void iValidatePageLoaded(String arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    log.info("Validate Page Loaded");
	    JSONObject response = new JSONObject(bodyText);
	    JSONObject expected = new JSONObject(SelectPartsJsonString(arg));
	    boolean same = JsonUtils.areEqual((Object) expected, (Object) response);
	    ErrorCollector.verifyTrue(same);
	}
}
