package com.felipecosta.microservice.server.frontcontroller.impl

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class HelloFrontCommand : FrontCommand() {
    override fun process() {
        render(template = "views/hello_world.html")
    }
}