package com.felipecosta.microservice.app.notes.frontcontroller

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class NotesFrontCommand : FrontCommand() {
    override fun process() {
        render(Output("My First Website", "My Interesting Content"), "views/notes.html")
    }
}