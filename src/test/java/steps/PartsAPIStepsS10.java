package steps;

import org.apache.log4j.Logger;

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
import interfaces.IGet;
import utils.ErrorCollector;
import utils.JsonUtils;

public class PartsAPIStepsS10 extends StepBase {
	
	static Logger log = Logger.getLogger(PartsAPIStepsS10.class.getName());
	String assumedAPI = "";
	String versionRequested = "";
	int responseCode;
	String bodyText = "";
	String responseHeader = "";
	
	@Before("@ptS10")
	public void setUp(Scenario scenario) throws Exception {
		log.info("running");
		super.setUp(scenario);
	}
	
	@After("@ptS10")
	public void tearDown() {
		log.info("teardown");
		super.tearDown();
	}
	
	
	private boolean expectError(String expected) {
		if (expected.contains("200")) { return false; }
		else {return true; }
	}
	
	private String buildExpectedMessage(String replace1, String replace2) {
		String tocheck = PartsAPIData.ERROR_MESSAGE_415.replace("XX", replace1);
		tocheck = tocheck.replaceAll("YY", replace2);
		return tocheck;
	}

	@And("^The current api version is ([^\"]*)$")
	public void theCurrentApiVersion(String version) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		log.info("api version is set to : " + version);
		// we need to set the expected value for checking in the error response
		assumedAPI = version;
	}

	@When("^the required version in the request is set to ([^\"]*)$")
	public void the_required_version_in_the_request_is_set_to(String version) throws Throwable {
	    
		log.info("api version requested is : " + version);
		versionRequested = version;
		// set up a default working request and send the request
		IGet lhttpget = new Get();
		lhttpget.createGetHTTP(LoadProperties.PARTS_API_URL + PartsAPIData.OFFSET + "?" + PartsAPIData.PARAM + "=", PartsAPIData.CFILTER_1);		
		lhttpget.setHeader("content-type", "application/json; " + version);
		lhttpget.setHeader("accept", "application/json");
		lhttpget.setHeader("Authorization", UtilitySteps.getMyToken());
		lhttpget.executeGetHTTP();
		log.info("return code was " + lhttpget.getResponseCode());
		log.info("the body was " + lhttpget.getResponseBody());
		log.info("the header was " + lhttpget.getResponseHeader("api-version"));
		responseCode = lhttpget.getResponseCode();
		responseHeader = lhttpget.getResponseHeader("api-version");
		bodyText = lhttpget.getResponseBody();
	}

	
	@Then("^I will get a response ([^\"]*)$")
	public void iGetResponse(String responsekey) throws Throwable {
	    // we check the code is correct
		// the message is correct
		// the response header is correct
		log.info("response expected is : " + responsekey);
		ErrorCollector.verifyEquals(assumedAPI, responseHeader );
		
		if (expectError(responsekey)) {
			ErrorCollector.verifyEquals(PartsLoadData.RESP_ERROR_4,responseCode );
			// check the message body contains the correct message
			String reference = buildExpectedMessage(versionRequested, assumedAPI);
			String errorDescription = JsonUtils.getErrorDescription(bodyText);
			ErrorCollector.verifyEquals(reference, errorDescription);
			
		} else {
			ErrorCollector.verifyEquals(PartsLoadData.RESP_SUCCESS,responseCode );
		}
		
	}
	
	
}
