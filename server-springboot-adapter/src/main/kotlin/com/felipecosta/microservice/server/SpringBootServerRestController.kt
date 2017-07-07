package com.felipecosta.microservice.server

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class SpringBootServerRestController {

    @RequestMapping("**")
    fun handler(request: HttpServletRequest): Any? {
        val path = request.pathInfo

        val action = ServerUrlMappings[path]
        if (action == null) {
            return ResponseEntity<Any?>(null, HttpStatus.NOT_FOUND)
        } else {
            return with(action.invoke()) { ResponseEntity<Any>(this.body, HttpStatus.valueOf(this.code)) }
        }
    }
}