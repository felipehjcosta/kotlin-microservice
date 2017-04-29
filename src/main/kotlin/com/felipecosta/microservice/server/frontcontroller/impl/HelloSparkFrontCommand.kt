package com.felipecosta.microservice.server.frontcontroller.impl

import com.felipecosta.microservice.server.frontcontroller.SparkFrontCommand

class HelloSparkFrontCommand : SparkFrontCommand() {
    override fun process() {
        render(template = "views/hello_world.html")
    }
}