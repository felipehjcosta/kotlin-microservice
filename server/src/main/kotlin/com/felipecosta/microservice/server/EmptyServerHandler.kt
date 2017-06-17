package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class EmptyServerHandler : ServerHandler {
    override fun get(getHandler: GetHandler<FrontCommand>) {
    }

    override fun post(postHandler: PostHandler<FrontCommand>) {
    }

    override fun put(putHandler: PutHandler<FrontCommand>) {
    }

    override fun delete(deleteHandler: DeleteHandler<FrontCommand>) {
    }
}