package com.felipecosta.microservice.server

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class ServerRestController {

    @GetMapping("**")
    fun handleGet(request: HttpServletRequest) = handlePath(request, GetPath(request.pathInfo))

    @PostMapping("**")
    fun handlePost(request: HttpServletRequest) = handlePath(request, PostPath(request.pathInfo))

    @PutMapping("**")
    fun handlePut(request: HttpServletRequest) = handlePath(request, PutPath(request.pathInfo))

    @DeleteMapping("**")
    fun handleDelete(request: HttpServletRequest) = handlePath(request, DeletePath(request.pathInfo))

    private fun handlePath(request: HttpServletRequest, actionHandler: ActionHandler): Any? =
            with(ServerUrlMappings[actionHandler]) {
                when (this) {
                    null -> ResponseEntity<Any?>(null, HttpStatus.NOT_FOUND)
                    else -> with(this(SpringBootRequestAdapter(request))) {
                        ResponseEntity<Any>(this.body, HttpStatus.valueOf(this.code))
                    }
                }
            }
}