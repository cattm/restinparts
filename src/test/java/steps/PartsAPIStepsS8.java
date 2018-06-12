package steps;

import org.apache.log4j.Logger;

import commons.LoadProperties;
import commons.PartsAPIData;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import http.Get;
import interfaces.IGet;
import utils.ErrorCollector;

public class PartsAPIStepsS8 extends StepBase {
	static Logger log = Logger.getLogger(PartsAPIStepsS8.class.getName());
	
	
	@Before("@ptS8")
	public void setUp(Scenario scenario) throws Exception {
		log.info("running");
		super.setUp(scenario);
	}
	
	@After("@ptS8")
	public void tearDown() {
		log.info("teardown");
		super.tearDown();
	}
	

	
	private String doRequest(String filter) {
		
		String configFilter = getConfigFilter(filter);
		IGet lhttpget = new Get();
		lhttpget.createGetHTTP(LoadProperties.PARTS_API_URL + PartsAPIData.OFFSET + "?" + PartsAPIData.PARAM + "=", configFilter);		
		lhttpget.useDefaultHeaders();
		lhttpget.setHeader("Authorization", UtilitySteps.getMyToken());
		lhttpget.executeGetHTTP();
		log.info("return code was " + lhttpget.getResponseCode());
		log.info("the body was " + lhttpget.getResponseBody());
		log.info("the header was " + lhttpget.getResponseHeader("api-version"));
		
		return lhttpget.getResponseBody();
		
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
	
	@Then("^multiple requests with the ([^\"]*) will yeild identical response$")
	public void multipleRequestsIdenticalResponse(String filter) throws Throwable {
	    log.info("MultipleRequests");
	    String response1 = doRequest(filter);
	    String response2 = doRequest(filter);
	    
	    ErrorCollector.verifyEquals(response1, response2 );
	}

}
