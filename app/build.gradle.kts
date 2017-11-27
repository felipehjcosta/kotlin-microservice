import org.gradle.kotlin.dsl.kotlin

group = "com.felipecosta.microservice"
version = "0.2"

plugins {
    kotlin("kapt")
}

dependencies {
    compile(project(":server"))
    compile(project(":server-spark-adapter"))
    compile("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    compile("org.jetbrains.kotlin:kotlin-reflect:${rootProject.extra["kotlin_version"]}")
    compile("io.lettuce:lettuce-core:5.0.0.RELEASE")

    testCompile("com.github.kstyrc:embedded-redis:0.6")
    testCompile("org.testcontainers:testcontainers:1.4.3")
    testCompile("io.cucumber:cucumber-java8:2.0.0-SNAPSHOT")
    testCompile("io.cucumber:cucumber-junit:2.0.0-SNAPSHOT")
    testCompile("io.cucumber:cucumber-picocontainer:2.0.0-SNAPSHOT")
    testCompile("com.github.kittinunf.fuel:fuel:1.12.0")
    testCompile("org.awaitility:awaitility:2.0.0")

    compile("com.google.dagger:dagger:2.4")
    kapt("com.google.dagger:dagger-compiler:2.4")
    compileOnly("javax.annotation:jsr250-api:1.0")
}

val jar: Jar by tasks

jar.apply {
    doLast {
        arrayOf("$rootDir/docker/debug", "$rootDir/docker/release").forEach { dest ->
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
            arrayOf("$rootDir/docker/debug", "$rootDir/docker/release").forEach { dest ->
                delete("$dest/app")
            }
        }
    }
}

java.sourceSets["main"].java {
    srcDir("$buildDir/generated/source/kapt/main")
}