package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class SpringBootServerHandler : ServerHandler {

    override fun get(getHandler: GetHandler<FrontCommand>) {
        ServerUrlMappings[getHandler.getPath] = FrontCommandFunction(getHandler.action)
    }

    override fun post(postHandler: PostHandler<FrontCommand>) {
        ServerUrlMappings[postHandler.postPath] = FrontCommandFunction(postHandler.action)
    }

    override fun put(putHandler: PutHandler<FrontCommand>) {
        ServerUrlMappings[putHandler.putPath] = FrontCommandFunction(putHandler.action)
    }

    override fun delete(deleteHandler: DeleteHandler<FrontCommand>) {
        ServerUrlMappings[deleteHandler.deletePath] = FrontCommandFunction(deleteHandler.action)
    }
}