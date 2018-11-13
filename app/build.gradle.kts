import org.gradle.kotlin.dsl.kotlin
import org.unbrokendome.gradle.plugins.testsets.TestSetsPlugin

group = "com.felipecosta.microservice"
version = "0.2"

plugins {
    id("org.unbroken-dome.test-sets").version("1.4.2")
    id("com.avast.gradle.docker-compose").version("0.7.1")
}

val acceptanceTest = testSets.create("acceptanceTest")

val acceptanceTestCompile = acceptanceTest.implementationConfigurationName

dependencies {
    compile(project(":server"))
    compile(project(":server-spark-adapter"))
    compile("io.lettuce:lettuce-core:5.0.0.RELEASE")

    testCompile("io.mockk:mockk:1.8.12.kotlin13")
    testCompile("com.github.kstyrc:embedded-redis:0.6")
    testCompile("org.testcontainers:testcontainers:1.4.3")

    acceptanceTestCompile("org.testcontainers:testcontainers:1.4.3")
    acceptanceTestCompile("io.cucumber:cucumber-java8:2.0.0-SNAPSHOT")
    acceptanceTestCompile("io.cucumber:cucumber-junit:2.0.0-SNAPSHOT")
    acceptanceTestCompile("io.cucumber:cucumber-picocontainer:2.0.0-SNAPSHOT")
    acceptanceTestCompile("com.github.kittinunf.fuel:fuel:1.12.0")
    acceptanceTestCompile("org.awaitility:awaitility:2.0.0")

    compile("com.github.salomonbrys.kodein:kodein:4.1.0")
}

val jar: Jar by tasks

val appJarDestinationPaths = arrayOf("$rootDir/docker/development",
        "$rootDir/docker/production",
        "$projectDir/src/acceptanceTest/resources/docker")

jar.apply {
    doLast {
        appJarDestinationPaths.forEach { dest ->
        copy {
            from(this@apply)
            into("$dest/app")
        }
    }
    }

    manifest {
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "com.felipecosta.microservice.BootstrapKt"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }

    archiveName = "$baseName.$extension"
}

tasks {
    "clean" {
        doFirst {
            appJarDestinationPaths.forEach { dest ->
                delete("$dest/app")
            }
        }
    }
}

dockerCompose {
    useComposeFiles = listOf("$rootDir/docker-compose.yml")
    isRequiredBy(tasks.getByName("acceptanceTest"))
}
