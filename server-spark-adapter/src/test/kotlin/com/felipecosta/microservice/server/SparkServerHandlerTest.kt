package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL
import kotlin.test.assertEquals

class SparkServerHandlerTest {

    @Test(timeout = 1000L)
    fun givenHandlerWhenGetItShouldAssertResponse() {
        val stubFrontCommand = object : FrontCommand() {
            override fun process() {
                render(text = "SparkServerHandler Integration Test")
            }
        }
        SparkServerHandler().apply {
            get(GetHandler(GetPath("/"), { stubFrontCommand }))
        }

        spark.Spark.awaitInitialization()

        val urlConnection = (URL("http://localhost:8080").openConnection() as HttpURLConnection).apply {
            readTimeout = 2000
            connectTimeout = 2000
            requestMethod = "GET"
        }
        val response = urlConnection.inputStream.reader().use { it.readText() }

        assertEquals("SparkServerHandler Integration Test", response)

        spark.Spark.stop()
    }

    @Test(timeout = 1000L)
    fun givenHandlerWhenPostItShouldAssertResponse() {
        val stubFrontCommand = object : FrontCommand() {
            override fun process() {
                render(text = "SparkServerHandler Integration Test")
            }
        }
        SparkServerHandler().apply {
            post(PostHandler(PostPath("/"), { stubFrontCommand }))
        }

        spark.Spark.awaitInitialization()

        val urlConnection = (URL("http://localhost:8080").openConnection() as HttpURLConnection).apply {
            readTimeout = 2000
            connectTimeout = 2000
            requestMethod = "POST"
        }
        val response = urlConnection.inputStream.reader().use { it.readText() }

        assertEquals("SparkServerHandler Integration Test", response)

        spark.Spark.stop()
    }

    @Test(timeout = 1000L)
    fun givenHandlerWhenPutItShouldAssertResponse() {
        val stubFrontCommand = object : FrontCommand() {
            override fun process() {
                render(text = "SparkServerHandler Integration Test")
            }
        }
        SparkServerHandler().apply {
            put(PutHandler(PutPath("/"), { stubFrontCommand }))
        }

        spark.Spark.awaitInitialization()

        val urlConnection = (URL("http://localhost:8080").openConnection() as HttpURLConnection).apply {
            readTimeout = 2000
            connectTimeout = 2000
            requestMethod = "PUT"
        }
        val response = urlConnection.inputStream.reader().use { it.readText() }

        assertEquals("SparkServerHandler Integration Test", response)

        spark.Spark.stop()
    }
}