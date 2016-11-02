package com.felipecosta.microservice.app.core.domain

interface UseCase<Argument, Response> {
    fun execute(argument: Argument? = null): Response
}