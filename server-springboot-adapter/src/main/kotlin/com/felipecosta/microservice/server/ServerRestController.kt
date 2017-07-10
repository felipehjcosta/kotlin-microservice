package com.felipecosta.microservice.server

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.AntPathMatcher
import org.springframework.web.bind.annotation.*
import java.util.regex.Pattern
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
            with(ServerUrlMappings.firstOrNull { matchPath(it.path, actionHandler.path) }) {
                when (this) {
                    null -> ResponseEntity<Any?>(null, HttpStatus.NOT_FOUND)
                    else -> with(this(SpringBootRequestAdapter(request))) {
                        ResponseEntity<Any>(this.body, HttpStatus.valueOf(this.code))
                    }
                }
            }

    private fun matchPath(registeredPath: String, requestpath: String) = with(AntPathMatcher()) {
        match(normalizePath(registeredPath), requestpath)
    }

    private fun normalizePath(path: String): String {
        val pattern = Pattern.compile("(:\\w+)|(\\w+)|(/)")
        val matcher = pattern.matcher(path)
        val builder = StringBuilder()
        while (matcher.find()) {
            val variable = matcher.group(0)
            if (variable.startsWith(":")) {
                builder.append("{${variable.replace(":", "")}}")
            } else {
                builder.append(variable)
            }
        }
        return builder.toString()
    }
}