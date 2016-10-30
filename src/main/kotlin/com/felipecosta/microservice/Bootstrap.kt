package com.felipecosta.microservice

import com.felipecosta.microservice.server.*
import com.felipecosta.microservice.server.frontcontroller.impl.HelloSparkFrontCommand
import com.felipecosta.microservice.server.frontcontroller.impl.JsonSparkFrontCommand
import com.felipecosta.microservice.server.frontcontroller.impl.NotesFrontCommand
import com.felipecosta.microservice.server.renderer.impl.PebbleRenderer
import com.mitchellbosecke.pebble.PebbleEngine

fun main(args: Array<String>) {
    val pebbleEngine = PebbleEngine.Builder().build()

    server {
        +(map get "/" to ::HelloSparkFrontCommand)
        +(map get "/json" to ::JsonSparkFrontCommand)
        +(map get "/notes" to ::NotesFrontCommand with PebbleRenderer(pebbleEngine))
    }
}
