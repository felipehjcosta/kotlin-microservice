package com.felipecosta.microservice.app.acceptancetests

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.string
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import cucumber.api.java8.En
import org.testcontainers.containers.DockerComposeContainer
import java.io.File
import kotlin.test.assertEquals


class KDockerComposeContainer(dockerFile: String) :
        DockerComposeContainer<KDockerComposeContainer>(File(dockerFile))

class ApiFeatureTest(webClient: WebClient) : En {

    lateinit var resultName: String

    init {

        Given("^a \"([^\"]*)\" movie$") { arg1: String ->
            val (_, response, _) = "${webClient.baseUrl}api/movies".httpPost().body("""{"name": "$arg1"}""").responseString()
            assertEquals(201, response.statusCode)
        }

        When("^request for a marvel movie$") {
            val (_, response, result) = "${webClient.baseUrl}api/movies/1".httpGet().responseString()
            resultName = ((Parser().parse(result.get().byteInputStream()) as JsonObject)["response"] as JsonObject)["name"] as String
        }

        Then("^I should be \"([^\"]*)\"$") { arg1: String ->
            assertEquals(arg1, resultName)
        }
    }

}