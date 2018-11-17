package com.felipecosta.microservice.server

import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import spark.QueryParamsMap
import kotlin.test.assertEquals

class SparkRequestAdapterTest {
    private val mockSparkRequest = mockk<spark.Request>()

    private val sparkRequestAdapter = SparkRequestAdapter(mockSparkRequest)

    @Test
    fun givenWrappedUrlItShouldAssertSameUrl() {
        every { mockSparkRequest.url() } returns "http://localhost:8080/hello"

        assertEquals("http://localhost:8080/hello", sparkRequestAdapter.url)
    }

    @Test
    fun givenWrappedHostItShouldAssertSameHost() {
        every { mockSparkRequest.host() } returns "http://localhost:8080"

        assertEquals("http://localhost:8080", sparkRequestAdapter.host)
    }

    @Test
    fun givenWrappedUserAgentItShouldAssertSameUserAgent() {
        every { mockSparkRequest.userAgent() } returns "Safari"

        assertEquals("Safari", sparkRequestAdapter.userAgent)
    }

    @Test
    fun givenWrappedBodyItShouldAssertSameBody() {
        every { mockSparkRequest.body() } returns "{}"

        assertEquals("{}", sparkRequestAdapter.body)
    }

    @Test
    fun givenWrappedQueryParamsItShouldAssertSameParams() {
        val mockQueryMap = mockk<QueryParamsMap>()
        every { mockQueryMap.toMap() } returns emptyMap()
        every { mockSparkRequest.queryMap() } returns mockQueryMap

        assertEquals(emptyMap(), sparkRequestAdapter.queryParams)
    }

    @Test
    fun givenWrappedRouteParamsItShouldAssertSameParams() {
        every { mockSparkRequest.params() } returns emptyMap()

        assertEquals(emptyMap(), sparkRequestAdapter.routeParams)
    }
}