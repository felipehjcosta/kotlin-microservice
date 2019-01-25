import io.gitlab.arturbosch.detekt.detekt

buildscript {

    val kotlinVersion by extra { "1.3.20" }
    val junitPlatformVersion by extra { "1.0.3" }
    val junitJupiterVersion by extra { "5.0.3" }
    val log4jVersion by extra { "2.9.0" }

    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.junit.platform:junit-platform-gradle-plugin:$junitPlatformVersion")
    }
}

plugins {
    base
    jacoco
    java
    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC12"
    id("com.vanniktech.dependency.graph.generator") version "0.2.0"
}

allprojects {

    val kotlinVersion: String by rootProject.extra
    val junitJupiterVersion: String by rootProject.extra
    val log4jVersion: String by rootProject.extra

    apply {
        plugin("kotlin")
        plugin("jacoco")
        plugin("org.junit.platform.gradle.plugin")
        plugin("io.gitlab.arturbosch.detekt")
    }

    repositories {
        jcenter()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

        // JUnit Jupiter API and TestEngine implementation
        testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

        // To use Log4J's LogManager
        testRuntime("org.apache.logging.log4j:log4j-core:$log4jVersion")
        testRuntime("org.apache.logging.log4j:log4j-jul:$log4jVersion")

        testCompile("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
        testCompile("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
        testCompile("io.mockk:mockk:1.8.12.kotlin13")
    }

    jacoco {
        toolVersion = "0.8.2"
    }

    tasks.withType<JacocoReport> {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
    }

    afterEvaluate {
        val junitPlatformTest: JavaExec by tasks
        jacoco {
            applyTo(junitPlatformTest)
        }
        task<JacocoReport>("jacocoJunit5TestReport") {
            executionData(junitPlatformTest)
            sourceSets(sourceSets["main"])
            additionalSourceDirs(files(sourceSets["main"].allSource.srcDirs))
            additionalClassDirs(files(sourceSets["main"].output))
        }
    }

    detekt {
        input = files("src/main/kotlin")
        filters = ".*/resources/.*,.*/build/.*"
        config = files("${project.rootDir}/default-detekt-config.yml")
    }
}
