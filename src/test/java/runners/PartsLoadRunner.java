package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "html:target/reports/cucumber-html-report",
				   "json:target/reports/cucumber.json",
				   "junit:target/reports/cucumber-results.xml",
				  "pretty:target/reports/cucumber-pretty.txt"},
		features = {"src/test/resources/features"},
		tags = {"@plS1, @plS2, @ptS1", "~@ignore"},
		glue = {"steps"}
		)
public class PartsLoadRunner {
//	public static Logger log = Logger.getLogger(CucumberRunner.class.getName());

}
