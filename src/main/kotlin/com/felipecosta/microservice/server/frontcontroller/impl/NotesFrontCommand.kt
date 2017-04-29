package com.felipecosta.microservice.server.frontcontroller.impl

import com.felipecosta.microservice.server.frontcontroller.SparkFrontCommand

class NotesFrontCommand : SparkFrontCommand() {
    override fun process() {
        render(Output("My First Website", "My Interesting Content"), "views/notes.html")
    }
}