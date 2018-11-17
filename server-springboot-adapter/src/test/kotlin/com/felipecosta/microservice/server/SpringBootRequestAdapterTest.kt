package com.felipecosta.microservice.server

import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import javax.servlet.http.HttpServletRequest
import kotlin.test.assertEquals

class SpringBootRequestAdapterTest {

    private val mockHttpServletRequest = mockk<HttpServletRequest>(relaxed = true)

    private val springBootRequestAdapter = SpringBootRequestAdapter(mockHttpServletRequest)

    @Test
    fun givenWrappedUrlItShouldAssertSameUrl() {
        every { mockHttpServletRequest.requestURL } returns StringBuffer("http://localhost:8080/hello")

        assertEquals("http://localhost:8080/hello", springBootRequestAdapter.url)
    }

    @Test
    fun givenWrappedHostItShouldAssertSameHost() {
        every { mockHttpServletRequest.scheme } returns "http"
        every { mockHttpServletRequest.serverName } returns "localhost"
        every { mockHttpServletRequest.serverPort } returns 8080

        assertEquals("http://localhost:8080", springBootRequestAdapter.host)
    }

    @Test
    fun givenWrappedUserAgentItShouldAssertSameUserAgent() {
        every { mockHttpServletRequest.getHeader("User-Agent") } returns "Safari"

        assertEquals("Safari", springBootRequestAdapter.userAgent)
    }

    @Test
    fun givenWrappedWithoutUserAgentItShouldAssertEmpty() {
        assertEquals("", springBootRequestAdapter.userAgent)
    }

    @Test
    fun givenWrappedQueryParamsItShouldAssertSameParams() {
        every { mockHttpServletRequest.parameterMap } returns emptyMap()

        assertEquals(emptyMap(), springBootRequestAdapter.queryParams)
    }

    @Test
    fun givenWrappedWithoutQueryParamsItShouldAssertSameParams() {
        every { mockHttpServletRequest.parameterMap } returns null

        assertEquals(emptyMap(), springBootRequestAdapter.queryParams)
    }
}