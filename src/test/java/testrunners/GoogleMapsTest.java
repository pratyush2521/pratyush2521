package testrunners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Pratyush
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/main/java/features/googlemaps.feature", glue = { "stepdefinitions" }, plugin = {
		"pretty" })
public class GoogleMapsTest {

}
