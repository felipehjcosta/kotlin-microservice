package com.felipecosta.microservice

import com.felipecosta.microservice.frontcontroller.impl.HelloSparkFrontCommand
import com.felipecosta.microservice.frontcontroller.impl.JsonSparkFrontCommand
import com.felipecosta.microservice.frontcontroller.impl.NotesFrontCommand
import com.felipecosta.microservice.server.server

fun main(args: Array<String>) {
    server {
        get("/hello-front-controller" to ::HelloSparkFrontCommand)
        get("/hello-front-controller-json" to ::JsonSparkFrontCommand)
        get("/notes" to ::NotesFrontCommand)
    }

}
