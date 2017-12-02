package com.felipecosta.microservice

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import cucumber.api.java8.En
import kotlin.test.assertEquals

class ApiFeatureTest(apiClient: ApiClient) : En {

    lateinit var resultName: String

    init {

        Given("^a \"([^\"]*)\" movie$") { arg1: String ->
            val (_, response, _) = "${apiClient.baseUrl}api/movies".httpPost().body("""{"name": "$arg1"}""").responseString()
            assertEquals(201, response.statusCode)
        }

        When("^request for a marvel movie$") {
            val (_, response, result) = "${apiClient.baseUrl}api/movies/1".httpGet().responseString()
            resultName = ((Parser().parse(result.get().byteInputStream()) as JsonObject)["response"] as JsonObject)["name"] as String
        }

        Then("^I should be \"([^\"]*)\"$") { arg1: String ->
            assertEquals(arg1, resultName)
        }
    }

}