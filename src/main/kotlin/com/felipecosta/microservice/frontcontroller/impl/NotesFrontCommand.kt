package com.felipecosta.microservice.frontcontroller.impl

import com.felipecosta.microservice.frontcontroller.SparkFrontCommand
import com.mitchellbosecke.pebble.PebbleEngine
import spark.Request
import spark.Response
import java.io.StringWriter

class NotesFrontCommand(request: Request, response: Response) : SparkFrontCommand(request, response) {
    override fun process() {

        val engine = PebbleEngine.Builder().build()

        val compiledTemplate = engine.getTemplate("views/notes.html")

        val writer = StringWriter()
        val context = mapOf("websiteTitle" to "My First Website", "content" to "My Interesting Content")
        compiledTemplate.evaluate(writer, context)
        val output = writer.toString()

        response.body(output)
    }
}