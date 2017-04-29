package com.felipecosta.microservice.server.frontcontroller.impl

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class NotesFrontCommand : FrontCommand() {
    override fun process() {
        render(Output("My First Website", "My Interesting Content"), "views/notes.html")
    }
}