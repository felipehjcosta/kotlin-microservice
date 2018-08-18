package com.felipecosta.microservice

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@cucumber.api.CucumberOptions(
        features = ["src/acceptanceTest/resources/features"],
        plugin = ["pretty", "html:build/reports/cucumber"]
)
class AcceptanceTestRunner