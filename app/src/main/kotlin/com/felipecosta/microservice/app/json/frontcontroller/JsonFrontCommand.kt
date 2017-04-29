package com.felipecosta.microservice.app.json.frontcontroller

import com.beust.klaxon.JsonArray
import com.beust.klaxon.json
import com.felipecosta.microservice.server.frontcontroller.FrontCommand

class JsonFrontCommand : FrontCommand() {

    override fun process() {

        val jsonBody = json {
            obj(
                    "url" to request.url,
                    "host" to request.host,
                    "user-agent" to request.userAgent,
                    "query-params" to request.params?.let { queryMap ->
                        array(queryMap.toMap().map { it ->
                            return@map obj(it.key to JsonArray(it.value.map { it }))
                        })
                    }
            )
        }

        render(text = jsonBody.toJsonString())
    }
}
