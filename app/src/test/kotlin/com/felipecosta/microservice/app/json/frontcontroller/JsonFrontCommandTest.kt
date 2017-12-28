package com.felipecosta.microservice.app.json.frontcontroller

import com.beust.klaxon.*
import com.felipecosta.microservice.server.Request
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JsonFrontCommandTest {

    private val request = mockk<Request>(relaxed = true)

    private val jsonSparkFrontCommand = JsonFrontCommand().apply { init(request) }

    @Test
    fun whenProcessThenAssertResponseWithUrl() {
        every { request.url } returns "http://127.0.0.1:4567/hello-front-controller-json"

        jsonSparkFrontCommand.process()

        val response = jsonSparkFrontCommand.response
        val responseBody = parse(response.body)
        assertEquals(200, response.code)
        assertEquals("http://127.0.0.1:4567/hello-front-controller-json", responseBody.string("url"))
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsHost() {
        every { request.host} returns "127.0.0.1:4567"

        jsonSparkFrontCommand.process()

        val response = jsonSparkFrontCommand.response
        val responseBody = parse(response.body)
        assertEquals(200, response.code)
        assertEquals("127.0.0.1:4567", responseBody.string("host"))
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsUserAgent() {
        every { request.userAgent} returns "Mozilla/5.0"

        jsonSparkFrontCommand.process()

        val response = jsonSparkFrontCommand.response
        val responseBody = parse(response.body)
        assertEquals(200, response.code)
        assertEquals("Mozilla/5.0", responseBody.string("user-agent"))
    }

    @Test
    fun GivenOneQueryParamWithOneParameterWhenProcessThenAssertResponseBodyContainsQueryParams() {
        every { request.queryParams} returns mapOf("q" to arrayOf("search"))

        jsonSparkFrontCommand.process()

        val response = jsonSparkFrontCommand.response
        val responseBody = parse(response.body)
        assertEquals(200, response.code)
        val searchQuery = responseBody.array<JsonObject>("query-params")!![0]["q"] as JsonArray<*>
        assertEquals(listOf("search"), searchQuery.toList())
    }

    @Test
    fun GivenOneQueryParamWithTwoParameterWhenProcessThenAssertResponseBodyContainsQueryParams() {
        every { request.queryParams} returns mapOf("q" to arrayOf("search", "awesome search"))

        jsonSparkFrontCommand.process()

        val response = jsonSparkFrontCommand.response
        val responseBody = parse(response.body)
        assertEquals(200, response.code)
        val searchQuery = responseBody.array<JsonObject>("query-params")!![0]["q"] as JsonArray<*>
        assertEquals(listOf("search", "awesome search"), searchQuery.toList())
    }

    private fun parse(json: String?): JsonObject {
        val byteArray = json!!.toByteArray()
        val inputStream = ByteInputStream(byteArray, byteArray.size)
        return Parser().parse(inputStream)!! as JsonObject
    }

}