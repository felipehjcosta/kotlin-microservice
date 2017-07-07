package com.felipecosta.microservice.server

internal object ServerUrlMappings {
    private val mapping = mutableMapOf<ActionHandler, () -> Response>()

    operator fun get(key: ActionHandler) = mapping[key]

    operator fun set(key: ActionHandler, value: () -> Response) {
        mapping[key] = value
    }

    fun clear() = mapping.clear()
}