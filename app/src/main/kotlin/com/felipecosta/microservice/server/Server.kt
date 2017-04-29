package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand
import com.felipecosta.microservice.server.renderer.Renderer
import spark.Spark

class Server {

    private val IP_ADDRESS = if (System.getenv("OPENSHIFT_DIY_IP") != null) System.getenv("OPENSHIFT_DIY_IP") else "localhost"
    private val PORT = if (System.getenv("OPENSHIFT_DIY_PORT") != null) Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) else 8080

    init {
        Spark.ipAddress(IP_ADDRESS)
        Spark.port(PORT)
    }

    operator fun GetHandlerWithRenderer.unaryPlus() {
        val routePath = get.getPath
        val action = get.action
        val renderer = renderer
        Spark.get(routePath.path) { request, _ ->
            val frontCommand: FrontCommand = action()
            frontCommand.init(Request(request), renderer)
            frontCommand.process()
            frontCommand.output
        }
    }

    operator fun GetHandler<FrontCommand>.unaryPlus() {
        val routePath = getPath
        val action = action
        Spark.get(routePath.path) { request, _ ->
            val frontCommand: FrontCommand = action()
            frontCommand.init(Request(request))
            frontCommand.process()
            frontCommand.output
        }
    }
}

inline fun <O> server(body: Server.() -> O): Server {
    val server = Server()
    server.body()
    return server
}

object map

infix fun map.get(path: String) = GetPath(path)

class GetPath(val path: String) {

}

infix fun <T : FrontCommand> GetPath.to(action: () -> T) = GetHandler(this, action)

class GetHandler<out T : FrontCommand>(val getPath: GetPath, val action: () -> T) {

}

infix fun <T : FrontCommand> GetHandler<T>.with(renderer: Renderer) = GetHandlerWithRenderer(this, renderer)

class GetHandlerWithRenderer(val get: GetHandler<FrontCommand>, val renderer: Renderer) {

}