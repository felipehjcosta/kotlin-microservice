package com.felipecosta.microservice.server.frontcontroller.impl

import com.beust.klaxon.JsonArray
import com.beust.klaxon.json
import com.felipecosta.microservice.server.frontcontroller.SparkFrontCommand

class JsonSparkFrontCommand : SparkFrontCommand() {

    override fun process() {

        val jsonBody = json {
            obj(
                    "url" to request.url(),
                    "host" to request.host(),
                    "user-agent" to request.userAgent(),
                    "query-params" to request.queryMap()?.let { queryMap ->
                        array(queryMap.toMap().map { it ->
                            return@map obj(it.key to JsonArray(it.value.map { it }))
                        })
                    }
            )
        }

        render(text = jsonBody.toJsonString())
    }
}
