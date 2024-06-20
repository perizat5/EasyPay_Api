package com.hp.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-reports.html",
                "rerun:target/rerun.txt",
                "me.jvt.cucumber.report.PrettyReports:target/cucumber",
                "json:target/cucumber.json"


        },
        features = "src/test/resources/features",
        glue = "com/hp/step_d",
        dryRun = false,
        tags = "@wip",
        publish = true
)
public class Runner {
}
