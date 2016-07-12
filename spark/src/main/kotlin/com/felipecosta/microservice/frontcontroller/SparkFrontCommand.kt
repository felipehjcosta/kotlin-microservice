package com.felipecosta.microservice.frontcontroller

import spark.Request
import spark.Response

abstract class SparkFrontCommand(val request: Request, val response: Response) : FrontCommand {
}