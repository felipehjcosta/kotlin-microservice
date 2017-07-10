package com.felipecosta.microservice.server

internal object ServerUrlMappings {
    private val mapping = mutableMapOf<ActionHandler, (request: Request) -> Response>()

    operator fun get(key: ActionHandler) = mapping[key]

    operator fun set(key: ActionHandler, value: (request: Request) -> Response) {
        mapping[key] = value
    }

    fun firstOrNull(predicate: (ActionHandler) -> Boolean) = mapping.asSequence().filter { predicate(it.key) }.firstOrNull()?.value

    fun clear() = mapping.clear()
}