package steps;

import org.apache.log4j.Logger;

import commons.PartsAPIData;
import cucumber.api.java.en.Given;

public class UtilitySteps {
	static Logger log = Logger.getLogger(UtilitySteps.class.getName());
	private static String token = "";
	
	@Given("^I have a valid ([^\"]*) that enables access to configurable parts$")
	public void iHaveValidNameEnablesAccess(String arg1) throws Throwable {
		log.info("I Have Valid Name : " + arg1);
		token = getConfigToken(arg1);
	}
	
	private String getConfigToken(String key) {
		// this method needs logic to work from feature file
		return PartsAPIData.EXAMPLE_TOKEN;
	}
	
	public static String getMyToken() { return token; }
}
