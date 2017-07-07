package com.felipecosta.microservice.server

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class SpringBootServerRestController {

    @GetMapping("**")
    fun handleGet(request: HttpServletRequest) = handlePath(GetPath(request.pathInfo))

    @PostMapping("**")
    fun handlePost(request: HttpServletRequest) = handlePath(PostPath(request.pathInfo))

    @PutMapping("**")
    fun handlePut(request: HttpServletRequest) = handlePath(PutPath(request.pathInfo))

    private fun handlePath(actionHandler: ActionHandler): Any? = with(ServerUrlMappings[actionHandler]) {
        when (this) {
            null -> ResponseEntity<Any?>(null, HttpStatus.NOT_FOUND)
            else -> with(this.invoke()) { ResponseEntity<Any>(this.body, HttpStatus.valueOf(this.code)) }
        }
    }
}