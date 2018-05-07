package com.felipecosta.microservice

import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.Before
import org.junit.runner.Description

class Environment {

    lateinit var junitDescription: Description

    val baseUrl: String
        get() = "http://0.0.0.0:8080/"

    @Before
    fun preScenario(scenario: Scenario) {
        junitDescription = Description.createSuiteDescription(scenario.name)
    }

    @After
    fun postScenario(scenario: Scenario) {
    }
}