package com.felipecosta.microservice.server

import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class SparkServerHandler : ServerHandler {

    private val IP_ADDRESS = if (System.getenv("OPENSHIFT_DIY_IP") != null) System.getenv("OPENSHIFT_DIY_IP") else "localhost"
    private val PORT = if (System.getenv("OPENSHIFT_DIY_PORT") != null) Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) else 8080

    init {
        spark.Spark.ipAddress(IP_ADDRESS)
        spark.Spark.port(PORT)
    }

    override fun get(getHandler: GetHandler<FrontCommand>) {
        val routePath = getHandler.getPath
        val action = getHandler.action
        val renderer = getHandler.renderer
        spark.Spark.get(routePath.path) { request, _ ->
            val frontCommand: FrontCommand = action()
            frontCommand.init(SparkRequestAdapter(request), renderer)
            frontCommand.process()
            frontCommand.output
        }
    }

    override fun post(postHandler: PostHandler<FrontCommand>) {
        val routePath = postHandler.postPath
        val action = postHandler.action
        val renderer = postHandler.renderer
        spark.Spark.post(routePath.path) { request, _ ->
            val frontCommand: FrontCommand = action()
            frontCommand.init(SparkRequestAdapter(request), renderer)
            frontCommand.process()
            frontCommand.output
        }
    }

    override fun put(putHandler: PutHandler<FrontCommand>) {
        val routePath = putHandler.putPath
        val action = putHandler.action
        val renderer = putHandler.renderer
        spark.Spark.put(routePath.path) { request, _ ->
            val frontCommand: FrontCommand = action()
            frontCommand.init(SparkRequestAdapter(request), renderer)
            frontCommand.process()
            frontCommand.output
        }
    }
}