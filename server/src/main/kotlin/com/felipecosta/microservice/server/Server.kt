package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer

class Server(internal var serverHandler: ServerHandler = EmptyServerHandler()) {

    fun handler(body: Server.() -> ServerHandler) {
        apply { serverHandler = body() }
    }

    operator fun GetHandler<FrontCommand>.unaryPlus() = serverHandler.get(this)
}

inline fun <O> server(body: Server.() -> O): Server = Server().apply { body() }

object map

infix fun map.get(path: String) = Path(path)

data class Path(val path: String)

infix fun <T : FrontCommand> Path.to(action: () -> T) = GetHandler(this, action)

data class GetHandler<out T : FrontCommand>(val path: Path, val action: () -> T, var renderer: Renderer = DefaultRenderer())

infix fun <T : FrontCommand> GetHandler<T>.with(newRenderer: Renderer) = this.apply { renderer = newRenderer }