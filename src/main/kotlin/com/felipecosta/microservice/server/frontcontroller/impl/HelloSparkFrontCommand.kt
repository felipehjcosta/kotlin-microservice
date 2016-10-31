package com.felipecosta.microservice.server.frontcontroller.impl

import com.felipecosta.microservice.server.frontcontroller.SparkFrontCommand
import spark.Request
import spark.Response

class HelloSparkFrontCommand() : SparkFrontCommand() {
    override fun process() {
        render(template = "views/hello_world.html")
    }
}