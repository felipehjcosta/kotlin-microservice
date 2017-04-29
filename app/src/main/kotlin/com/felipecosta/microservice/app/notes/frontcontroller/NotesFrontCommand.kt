package com.felipecosta.microservice.app.notes.frontcontroller

class NotesFrontCommand : com.felipecosta.microservice.server.frontcontroller.FrontCommand() {
    override fun process() {
        render(Output("My First Website", "My Interesting Content"), "views/notes.html")
    }
}