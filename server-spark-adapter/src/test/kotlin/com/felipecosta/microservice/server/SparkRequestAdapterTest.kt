package com.felipecosta.microservice.server

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import spark.QueryParamsMap
import kotlin.test.assertEquals

class SparkRequestAdapterTest {
    val mockSparkRequest = mock<spark.Request>()

    val sparkRequestAdapter = SparkRequestAdapter(mockSparkRequest)

    @Test
    fun givenWrappedUrlItShouldAssertSameUrl() {
        whenever(mockSparkRequest.url()).thenReturn("http://localhost:8080/hello")

        assertEquals("http://localhost:8080/hello", sparkRequestAdapter.url)
    }

    @Test
    fun givenWrappedHostItShouldAssertSameHost() {
        whenever(mockSparkRequest.host()).thenReturn("http://localhost:8080")

        assertEquals("http://localhost:8080", sparkRequestAdapter.host)
    }

    @Test
    fun givenWrappedUserAgentItShouldAssertSameUserAgent() {
        whenever(mockSparkRequest.userAgent()).thenReturn("Safari")

        assertEquals("Safari", sparkRequestAdapter.userAgent)
    }

    @Test
    fun givenWrappedBodyUserAgentItShouldAssertSameUserAgent() {
        whenever(mockSparkRequest.body()).thenReturn("{}")

        assertEquals("{}", sparkRequestAdapter.body)
    }

    @Test
    fun givenWrappedQueryParamsItShouldAssertSameParams() {
        val mockQueryMap = mock<QueryParamsMap>()
        whenever(mockQueryMap.toMap()).thenReturn(emptyMap())
        whenever(mockSparkRequest.queryMap()).thenReturn(mockQueryMap)

        assertEquals(emptyMap(), sparkRequestAdapter.queryParams)
    }

    @Test
    fun givenWrappedRouteParamsItShouldAssertSameParams() {
        whenever(mockSparkRequest.params()).thenReturn(emptyMap())

        assertEquals(emptyMap(), sparkRequestAdapter.routeParams)
    }
}