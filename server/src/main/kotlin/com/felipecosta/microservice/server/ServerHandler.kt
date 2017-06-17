package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

interface ServerHandler {
    fun get(getHandler: GetHandler<FrontCommand>)

    fun post(postHandler: PostHandler<FrontCommand>)

    fun put(putHandler: PutHandler<FrontCommand>)

    fun delete(deleteHandler: DeleteHandler<FrontCommand>)
}