package com.felipecosta.microservice.server

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class SpringBootServerRestController {

    @GetMapping("**")
    fun handleGet(request: HttpServletRequest): Any? {
        val path = request.pathInfo

        val action = ServerUrlMappings[GetPath(path)]
        if (action == null) {
            return ResponseEntity<Any?>(null, HttpStatus.NOT_FOUND)
        } else {
            return with(action.invoke()) { ResponseEntity<Any>(this.body, HttpStatus.valueOf(this.code)) }
        }
    }

    @PostMapping("**")
    fun handlePost(request: HttpServletRequest): Any? {
        val action = ServerUrlMappings[PostPath(request.pathInfo)]
        if (action == null) {
            return ResponseEntity<Any?>(null, HttpStatus.NOT_FOUND)
        } else {
            return with(action.invoke()) { ResponseEntity<Any>(this.body, HttpStatus.valueOf(this.code)) }
        }
    }
}