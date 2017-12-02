package com.felipecosta.microservice

import org.testcontainers.containers.DockerComposeContainer
import java.io.File

class KDockerComposeContainer(dockerFile: String) :
        DockerComposeContainer<KDockerComposeContainer>(File(dockerFile))