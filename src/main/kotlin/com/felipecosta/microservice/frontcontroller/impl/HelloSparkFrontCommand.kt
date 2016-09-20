package com.felipecosta.microservice.frontcontroller.impl

import com.felipecosta.microservice.frontcontroller.SparkFrontCommand
import spark.Request
import spark.Response

class HelloSparkFrontCommand(request: Request, response: Response) : SparkFrontCommand(request, response) {
    override fun process() {
        response.body("Hello World form Spark Front Command")
    }
}