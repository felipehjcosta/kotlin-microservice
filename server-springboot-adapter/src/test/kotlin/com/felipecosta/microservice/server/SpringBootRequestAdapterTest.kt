package com.felipecosta.microservice.server

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import javax.servlet.http.HttpServletRequest
import kotlin.test.assertEquals

class SpringBootRequestAdapterTest {

    val mockHttpServletRequest = mock<HttpServletRequest>()

    val springBootRequestAdapter = SpringBootRequestAdapter(mockHttpServletRequest)

    @Test
    fun givenWrappedUrlItShouldAssertSameUrl() {
        whenever(mockHttpServletRequest.requestURL).thenReturn(StringBuffer("http://localhost:8080/hello"))

        assertEquals("http://localhost:8080/hello", springBootRequestAdapter.url)
    }

    @Test
    fun givenWrappedHostItShouldAssertSameHost() {
        whenever(mockHttpServletRequest.scheme).thenReturn("http")
        whenever(mockHttpServletRequest.serverName).thenReturn("localhost")
        whenever(mockHttpServletRequest.serverPort).thenReturn(8080)

        assertEquals("http://localhost:8080", springBootRequestAdapter.host)
    }

    @Test
    fun givenWrappedUserAgentItShouldAssertSameUserAgent() {
        whenever(mockHttpServletRequest.getHeader("User-Agent")).thenReturn("Safari")

        assertEquals("Safari", springBootRequestAdapter.userAgent)
    }

    @Test
    fun givenWrappedWithoutUserAgentItShouldAssertEmpty() {
        assertEquals("", springBootRequestAdapter.userAgent)
    }

    @Test
    fun givenWrappedQueryParamsItShouldAssertSameParams() {
        whenever(mockHttpServletRequest.parameterMap).thenReturn(emptyMap())

        assertEquals(emptyMap(), springBootRequestAdapter.queryParams)
    }

    @Test
    fun givenWrappedWithoutQueryParamsItShouldAssertSameParams() {
        assertEquals(emptyMap(), springBootRequestAdapter.queryParams)
    }
}