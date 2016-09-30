package com.felipecosta.microservice

import com.felipecosta.microservice.frontcontroller.impl.HelloSparkFrontCommand
import com.felipecosta.microservice.frontcontroller.impl.JsonSparkFrontCommand
import com.felipecosta.microservice.frontcontroller.impl.NotesFrontCommand
import com.felipecosta.microservice.renderer.impl.PebbleRenderer
import com.felipecosta.microservice.server.*
import com.mitchellbosecke.pebble.PebbleEngine

fun main(args: Array<String>) {
    val pebbleEngine = PebbleEngine.Builder().build()

    server {
        +(map get "/" to ::HelloSparkFrontCommand)
        +(map get "/json" to ::JsonSparkFrontCommand)
        +(map get "/notes" to ::NotesFrontCommand with PebbleRenderer(pebbleEngine))
    }
}
