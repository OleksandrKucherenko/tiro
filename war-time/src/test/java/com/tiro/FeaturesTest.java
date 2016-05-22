package com.tiro;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/** Run all cucumber feature tests. */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class FeaturesTest {
}
