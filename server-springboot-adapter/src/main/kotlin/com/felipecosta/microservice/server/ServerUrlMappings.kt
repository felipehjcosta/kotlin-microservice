package com.felipecosta.microservice.server

object ServerUrlMappings {
    private val mapping = mutableMapOf<String, () -> Response>()

    operator fun get(key: String) = mapping[key]

    operator fun set(key: String, value: () -> Response) {
        mapping[key] = value
    }

    fun clear() = mapping.clear()
}