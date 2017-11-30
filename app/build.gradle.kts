import org.gradle.kotlin.dsl.kotlin
import org.unbrokendome.gradle.plugins.testsets.TestSetsPlugin

group = "com.felipecosta.microservice"
version = "0.2"

plugins {
    kotlin("kapt")
    id("org.unbroken-dome.test-sets").version("1.4.2")
}

val acceptanceTest = testSets.create("acceptanceTest")

val acceptanceTestCompile = acceptanceTest.implementationConfigurationName

dependencies {
    compile(project(":server"))
    compile(project(":server-spark-adapter"))
    compile("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    compile("org.jetbrains.kotlin:kotlin-reflect:${rootProject.extra["kotlin_version"]}")
    compile("io.lettuce:lettuce-core:5.0.0.RELEASE")

    testCompile("com.github.kstyrc:embedded-redis:0.6")
    testCompile("org.testcontainers:testcontainers:1.4.3")

    acceptanceTestCompile("org.testcontainers:testcontainers:1.4.3")
    acceptanceTestCompile("io.cucumber:cucumber-java8:2.0.0-SNAPSHOT")
    acceptanceTestCompile("io.cucumber:cucumber-junit:2.0.0-SNAPSHOT")
    acceptanceTestCompile("io.cucumber:cucumber-picocontainer:2.0.0-SNAPSHOT")
    acceptanceTestCompile("com.github.kittinunf.fuel:fuel:1.12.0")
    acceptanceTestCompile("org.awaitility:awaitility:2.0.0")

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

        copy {
            from("$rootDir/docker/debug")
            into("src/${acceptanceTest.sourceSetName}/resources/docker")
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

            delete("src/${acceptanceTest.sourceSetName}/resources/docker")
        }
    }
}

java.sourceSets["main"].java {
    srcDir("$buildDir/generated/source/kapt/main")
}