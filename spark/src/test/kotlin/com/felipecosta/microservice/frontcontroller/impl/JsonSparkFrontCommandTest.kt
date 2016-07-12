package com.felipecosta.microservice.frontcontroller.impl

import com.beust.klaxon.*
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import spark.QueryParamsMap
import spark.Request
import spark.Response
import kotlin.test.assertEquals
import kotlin.test.assertNull

class JsonSparkFrontCommandTest {

    val request: Request = mock()

    val response: Response = mock()

    var jsonSparkFrontCommand: JsonSparkFrontCommand? = null

    @Before
    fun setUp() {
        jsonSparkFrontCommand = JsonSparkFrontCommand(request, response)
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsUrl() {
        com.nhaarman.mockito_kotlin.whenever(request.url()).thenReturn("http://127.0.0.1:4567/hello-front-controller-json")

        jsonSparkFrontCommand?.process()
        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(response).body(argumentCaptor.capture())

        val responseBody = parse(argumentCaptor.value)
        assertEquals("http://127.0.0.1:4567/hello-front-controller-json", responseBody.string("url"))
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsHost() {
        com.nhaarman.mockito_kotlin.whenever(request.host()).thenReturn("127.0.0.1:4567")

        jsonSparkFrontCommand?.process()
        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(response).body(argumentCaptor.capture())

        val responseBody = parse(argumentCaptor.value)
        assertEquals("127.0.0.1:4567", responseBody.string("host"))
    }

    @Test
    fun whenProcessThenAssertResponseBodyContainsUserAgent() {
        com.nhaarman.mockito_kotlin.whenever(request.userAgent()).thenReturn("Mozilla/5.0")

        jsonSparkFrontCommand?.process()
        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(response).body(argumentCaptor.capture())

        val responseBody = parse(argumentCaptor.value)
        assertEquals("Mozilla/5.0", responseBody.string("user-agent"))
    }

    @Test
    fun GivenOneQueryParamWithOneParameterWhenProcessThenAssertResponseBodyContainsQueryParams() {
        val queryMap: QueryParamsMap = mock()
        com.nhaarman.mockito_kotlin.whenever(request.queryMap()).thenReturn(queryMap)
        com.nhaarman.mockito_kotlin.whenever(queryMap.toMap()).thenReturn(mapOf("q" to arrayOf("search")))

        jsonSparkFrontCommand?.process()
        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(response).body(argumentCaptor.capture())

        val responseBody = parse(argumentCaptor.value)
        val searchQuery = responseBody.array<com.beust.klaxon.JsonObject>("query-params")!![0]["q"] as JsonArray<*>
        assertEquals(listOf("search"), searchQuery.toList())
    }

    @Test
    fun GivenOneQueryParamWithTwoParameterWhenProcessThenAssertResponseBodyContainsQueryParams() {
        val queryMap: QueryParamsMap = mock()
        com.nhaarman.mockito_kotlin.whenever(request.queryMap()).thenReturn(queryMap)
        com.nhaarman.mockito_kotlin.whenever(queryMap.toMap()).thenReturn(mapOf("q" to arrayOf("search", "awesome search")))

        jsonSparkFrontCommand?.process()
        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(response).body(argumentCaptor.capture())

        val responseBody = parse(argumentCaptor.value)
        val searchQuery = responseBody.array<com.beust.klaxon.JsonObject>("query-params")!![0]["q"] as JsonArray<*>
        assertEquals(listOf("search", "awesome search"), searchQuery.toList())
    }

    @Test
    fun GivenNullQueryParamWhenProcessThenAssertResponseBodyNotContainQueryParams() {
        com.nhaarman.mockito_kotlin.whenever(request.queryMap()).thenReturn(null)

        jsonSparkFrontCommand?.process()
        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(response).body(argumentCaptor.capture())

        val responseBody = parse(argumentCaptor.value)
        assertNull(responseBody.array<com.beust.klaxon.JsonObject>("query-params"))
    }

    private fun parse(json: String): JsonObject {
        val byteArray = json.toByteArray()
        val inputStream = ByteInputStream(byteArray, byteArray.size)
        return Parser().parse(inputStream)!! as JsonObject
    }


}