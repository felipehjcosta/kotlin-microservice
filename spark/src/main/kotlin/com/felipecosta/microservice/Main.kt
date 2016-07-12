package com.felipecosta.microservice

import com.felipecosta.microservice.frontcontroller.impl.HelloSparkFrontCommand
import com.felipecosta.microservice.frontcontroller.impl.JsonSparkFrontCommand
import com.felipecosta.microservice.server.server
import spark.Spark.get

fun main(args: Array<String>) {
    // The best Hello World ever :)
    get("/hello") { request, response -> "Hello World from spark" }

    server {
        get("/hello-front-controller" to ::HelloSparkFrontCommand)
        get("/hello-front-controller-json" to ::JsonSparkFrontCommand)
    }

}
