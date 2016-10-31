package com.felipecosta.microservice.server.frontcontroller.impl

import com.felipecosta.microservice.server.frontcontroller.SparkFrontCommand
import spark.Request
import spark.Response

class NotesFrontCommand : SparkFrontCommand() {
    override fun process() {
        render(Output("My First Website", "My Interesting Content"), "views/notes.html")
    }

    data class Output(val title: String, val content: String) {

    }
}