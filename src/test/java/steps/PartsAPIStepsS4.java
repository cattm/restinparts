package steps;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import commons.LoadProperties;
import commons.PartsAPIData;
import commons.PartsAPIData;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import http.Get;
import interfaces.IGet;
import utils.ErrorCollector;
import utils.JsonUtils;

public class PartsAPIStepsS4 extends StepBase {

	static Logger log = Logger.getLogger(PartsAPIStepsS4.class.getName());
	private boolean setHeaders = true;
	private String contentHdr = "application/json";
	private String acceptHdr = "application/json";
	private int responseCode = 0;
	private String responseBody = "";


	@Before("@ptS4")
	public void setUp(Scenario scenario) throws Exception {
		log.info("running");
		super.setUp(scenario);
	}
	
	@After("@ptS4")
	public void tearDown() {
		log.info("teardown");
		super.tearDown();
	}

	
	public String selectErrorString(String key) {
		if (key.contentEquals("415_MSG_4")) { return PartsAPIData.ERROR_4_TEXT;}
		else if (key.contentEquals("400_MSG_1")) { return PartsAPIData.ERROR_1_TEXT;}
		else if (key.contentEquals("400_MSG_2")) { return PartsAPIData.ERROR_2_TEXT;}
		else if (key.contentEquals("400_MSG_7")) { return PartsAPIData.ERROR_7_TEXT;}
		else if (key.contentEquals("400_MSG_8")) { return PartsAPIData.ERROR_8_TEXT;}
		else if (key.contentEquals("406_MSG_3")) { return PartsAPIData.ERROR_3_TEXT;}
		else if (key.contentEquals("415_MSG_5")) { return PartsAPIData.ERROR_5_TEXT;}
	
		else return "";
	}
	
	public int selectErrorCode(String key) {
		if (key.contentEquals("415_MSG_4")) { return PartsAPIData.RESP_ERROR_4;}
		else if (key.contentEquals("400_MSG_1")) { return PartsAPIData.RESP_ERROR_1;}
		else if (key.contentEquals("400_MSG_2")) { return PartsAPIData.RESP_ERROR_1;}
		else if (key.contentEquals("400_MSG_7")) { return PartsAPIData.RESP_ERROR_1;}
		else if (key.contentEquals("400_MSG_8")) { return PartsAPIData.RESP_ERROR_1;}
		else if (key.contentEquals("406_MSG_3")) { return PartsAPIData.RESP_ERROR_3;}
		else if (key.contentEquals("415_MSG_5")) { return PartsAPIData.RESP_ERROR_4;}
		
		else return PartsAPIData.RESP_SUCCESS;
	}
	@And("^I set the Get Request ([^\"]*) to ([^\"]*)$")
	public void setGetRequestHeader(String header, String value) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		log.info("I request header : " + header + " to " + value);
	    if (header.contentEquals("Content-Type")) {contentHdr = value;}
	    else if (header.contentEquals("Accept")) {acceptHdr = value;}
	    else if (header.contentEquals("none")) { setHeaders = false; }
	}

	@When("^I set the request to an ([^\"]*) via the API$")
	public void iSetTheRequestViaAPI(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		log.info("I Send Invalid Request Message : " + arg1);
		IGet lhttpget = new Get();
		lhttpget.createGetHTTP(LoadProperties.PARTS_API_URL + PartsAPIData.OFFSET + "?" , arg1);
		if (setHeaders) {
			lhttpget.setHeader("content-type", contentHdr);
			lhttpget.setHeader("accept", acceptHdr);
		}
		lhttpget.setHeader("Authorization", UtilitySteps.getMyToken());
		
		lhttpget.executeGetHTTP();
		log.info("return code was " + lhttpget.getResponseCode());
		log.info("the body was " + lhttpget.getResponseBody());
		responseCode = lhttpget.getResponseCode();
		responseBody = lhttpget.getResponseBody();
	}


	@Then("^Response will be ([^\"]*) message$")
	public void reposneWillBeMessage(String message) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		log.info("The response is defined by : " + message);
		String checkerrormsg = selectErrorString(message);
	    int checkcode = selectErrorCode(message);
	    ErrorCollector.verifyEquals(responseCode, checkcode);
	    JSONObject start = new JSONObject(checkerrormsg);
	    JSONObject finish = new JSONObject(responseBody);
	    boolean same = JsonUtils.areEqual((Object) start, (Object) finish);
	    ErrorCollector.verifyTrue(same);
	}

}
