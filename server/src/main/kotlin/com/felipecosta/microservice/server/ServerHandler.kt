package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

interface ServerHandler {
    fun get(getHandler: GetHandler<FrontCommand>)
}