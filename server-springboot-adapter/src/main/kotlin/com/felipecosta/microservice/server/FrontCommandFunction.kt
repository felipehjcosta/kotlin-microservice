package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class FrontCommandFunction(private val frontCommandCreateAction: () -> FrontCommand) : Function1<Request, Response> {
    override fun invoke(request: Request): Response = with(frontCommandCreateAction()) {
        init(request)
        process()
        response
    }
}
