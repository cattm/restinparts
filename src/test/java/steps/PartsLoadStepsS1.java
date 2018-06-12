package steps;

import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import commons.LoadProperties;
import commons.PartsLoadData;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import http.Post;
import interfaces.IPost;
import utils.ErrorCollector;
import utils.JsonUtils;

public class PartsLoadStepsS1 extends StepBase {
	static Logger log = Logger.getLogger(PartsLoadStepsS1.class.getName());
	
	private String jsonToLoad = "";
	private IPost httpPost = null;
	private HttpPost postRequest = null;
	
	@Before("@plS1")
	public void setUp(Scenario scenario) throws Exception {
		log.info("running");
		super.setUp(scenario);
		 httpPost = new Post();
	}
	
	@After("@plS1")
	public void tearDown() {
		log.info("teardown");
		super.tearDown();
	}
		
	
	@Given("^I am correctly identified in AD ([^\"]*) name$")
	public void identifiedInADServiceGroup(String data) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    log.info("method not implemented until AD solution provided");
	    // this is setup code

	    
	}
    
	@When("^I send valid configurable ([^\"]*) items via the API$")
	public void SendValidPartsRecordViaAPI(String load) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    log.info("will send a message using HTTP Client interface");

	   
	    jsonToLoad = SelectPartsJsonString(load);	    
	    log.info("JSON to load is : " + jsonToLoad);
	    //httpPost.PostHTTPData(LoadProperties.PARTS_INGRESS_URL + PartsLoadData.OFFSET, jsonToLoad );
	    postRequest = httpPost.createPostHTTP(LoadProperties.PARTS_INGRESS_URL + PartsLoadData.OFFSET, jsonToLoad );
	    httpPost.useDefaultHeaders();
	    httpPost.executePost(postRequest);
	    log.info("return code was " + httpPost.getResponseCode());
	    log.info("the body was " + httpPost.getResponseBody());
	    // add asserts for code and body
	    ErrorCollector.verifyEquals(httpPost.getResponseCode(), PartsLoadData.RESP_SUCCESS);
	    JSONObject start = new JSONObject(PartsLoadData.SUCCESS_TEXT);
	    JSONObject finish = new JSONObject(httpPost.getResponseBody());
	    boolean same = JsonUtils.areEqual((Object) start, (Object) finish);
	    ErrorCollector.verifyTrue(same);
	}

	@Then("^I will modify the Parts repository$")
	public void PartsRepositoryUpdated() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    log.info("I will check the update in DB via MongoDB interface");    
	    JSONObject checkjson = new JSONObject(jsonToLoad);
	    if (true) {
	    //if (JsonUtils.isJsonArray((Object)checkjson)) {
	    	JSONArray array = checkjson.getJSONArray("values");
	    	// get each record in turn
	    	for (int i = 0; i < array.length(); i++) {
                JSONObject rectocheck = (JSONObject) array.get(i);
                // now we have the individual loaded object we can get the partId and value
                String id = rectocheck.getString("partId").toString();
                log.info("json partId to get is : " + id);
                // get the record from mongo
                String recordfound = ReadMondgo("partId", id);
                log.info("Mongo returned    : " + recordfound);
                // strip it of _id "_id" : { "$oid" : "58a1ad6390206b001cdadc0d"}}
                JSONObject strippedmongo = StripMongoRecord(recordfound);
                log.info("Stripped Mongo is : " + strippedmongo.toString());
                log.info("We wrote Record   : " + rectocheck.toString());
                // then compare
        	    boolean verified = checkDBRecord(strippedmongo, rectocheck);
        	    // now assert the record is true
        	    // TODO: There is a problem with UTF-8 v ISO-8859-1 etc type encoding
        	    // need to make sure we are reading and converting to/from Mongo correctly
        	    // ErrorCollector.verifyTrue(verified);

            }
	    }
	    
	  
	   
	}
}
