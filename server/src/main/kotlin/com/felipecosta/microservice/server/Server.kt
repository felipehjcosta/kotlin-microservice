package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import com.felipecosta.microservice.server.renderer.Renderer
import com.felipecosta.microservice.server.renderer.impl.DefaultRenderer

class Server(internal var serverHandler: ServerHandler = EmptyServerHandler()) {

    fun handler(body: Server.() -> ServerHandler) {
        apply { serverHandler = body() }
    }

    operator fun GetHandler<FrontCommand>.unaryPlus() = serverHandler.get(this)

    operator fun PostHandler<FrontCommand>.unaryPlus() = serverHandler.post(this)

    operator fun PutHandler<FrontCommand>.unaryPlus() = serverHandler.put(this)
}

inline fun <O> server(body: Server.() -> O): Server = Server().apply { body() }

object map


infix fun map.get(path: String) = GetPath(path)

data class GetPath(val path: String)

infix fun <T : FrontCommand> GetPath.to(action: () -> T) = GetHandler(this, action)

data class GetHandler<out T : FrontCommand>(val getPath: GetPath, val action: () -> T, var renderer: Renderer = DefaultRenderer())

infix fun <T : FrontCommand> GetHandler<T>.with(newRenderer: Renderer) = this.apply { renderer = newRenderer }


infix fun map.post(path: String) = PostPath(path)

data class PostPath(val path: String)

infix fun <T : FrontCommand> PostPath.to(action: () -> T) = PostHandler(this, action)

data class PostHandler<out T : FrontCommand>(val postPath: PostPath, val action: () -> T, var renderer: Renderer = DefaultRenderer())

infix fun <T : FrontCommand> PostHandler<T>.with(newRenderer: Renderer) = this.apply { renderer = newRenderer }


infix fun map.put(path: String) = PutPath(path)

data class PutPath(val path: String)

infix fun <T : FrontCommand> PutPath.to(action: () -> T) = PutHandler(this, action)

data class PutHandler<out T : FrontCommand>(val putPath: PutPath, val action: () -> T, var renderer: Renderer = DefaultRenderer())

infix fun <T : FrontCommand> PutHandler<T>.with(newRenderer: Renderer) = this.apply { renderer = newRenderer }