package com.felipecosta.microservice

import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.Before
import org.awaitility.Awaitility
import org.awaitility.Duration
import org.junit.runner.Description
import org.testcontainers.DockerClientFactory
import org.testcontainers.dockerclient.LogToStringContainerCallback
import java.util.concurrent.Callable

class Environment {

    lateinit var junitDescription: Description

    lateinit var environment: KDockerComposeContainer

    val baseUrl: String
        get() = "http://${environment.getServiceHost("app_1", 8080)}" +
                ":${environment.getServicePort("app_1", 8080)}/"

    @Before
    fun preScenario(scenario: Scenario) {
        junitDescription = Description.createSuiteDescription(scenario.name)

        environment = KDockerComposeContainer("src/acceptanceTest/resources/docker/docker-compose.yml")
                .withExposedService("app_1", 8080)
                .withLocalCompose(true)

        environment.starting(junitDescription)

        Awaitility.with().pollInterval(Duration.FIVE_HUNDRED_MILLISECONDS)
                .and().atMost(Duration.TEN_SECONDS)
                .await("Test environment started")
                .until(Callable<Boolean> {
                    val hostIp = getProxyHost()
                    return@Callable hostIp.isNotEmpty()
                })
    }

    private fun getProxyHost(): String = DockerClientFactory.instance()
            .runInsideDocker({ cmd -> cmd.withCmd("sh", "-c", "ip route|awk '/default/ { print $3 }'") }
            ) { client, id ->
                try {
                    return@runInsideDocker client.logContainerCmd(id)
                            .withStdOut(true)
                            .exec(LogToStringContainerCallback())
                            .toString()
                } catch (e: Exception) {
                    return@runInsideDocker ""
                }
            }.trim()

    @After
    fun postScenario(scenario: Scenario) {
        environment.finished(junitDescription)
    }
}