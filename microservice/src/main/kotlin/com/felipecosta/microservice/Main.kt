package com.felipecosta.microservice

import spark.Spark.get

fun main(args: Array<String>) {
    get("/hello") { request, response -> "Hello World from spark" }
}
