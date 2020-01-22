package com.automation;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        tags = {"@ui"},
        plugin = {"progress", "html:target/reports"})
public class RunCucumberTest {
// If you want to run the API tests using this Runner, then please change the tags from @ui to @api
}
