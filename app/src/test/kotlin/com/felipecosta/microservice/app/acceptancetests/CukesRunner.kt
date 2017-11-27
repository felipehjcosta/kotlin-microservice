package com.felipecosta.microservice.app.acceptancetests

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@cucumber.api.CucumberOptions(
        features = arrayOf("src/test/resources/features"),
        plugin = arrayOf("pretty", "html:build/reports/cucumber")
)
class CukesRunner