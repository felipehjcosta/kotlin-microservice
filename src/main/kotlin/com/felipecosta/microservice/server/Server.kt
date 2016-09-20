package com.felipecosta.microservice.server

import com.felipecosta.microservice.frontcontroller.FrontCommand
import spark.Request
import spark.Response
import spark.Spark

class Server {

    fun <T : FrontCommand> get(pair: Pair<String, (request: Request, response: Response) -> T>) {
        Spark.get(pair.first) { request, response ->
            val frontCommand: FrontCommand = pair.second(request, response)
            frontCommand.process()
            response.body()
        }
    }

}

inline fun <O> server(body: Server.() -> O): Server {
    val server = Server()
    server.body()
    return server
}

