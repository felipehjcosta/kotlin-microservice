buildscript {

    val kotlinVersion by extra { "1.2.61" }
    val junitPlatformVersion by extra { "1.0.3" }
    val junit4Version by extra { "4.12" }
    val junitVintageVersion by extra { "4.12.3" }
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
    id("com.vanniktech.dependency.graph.generator").version("0.2.0")
}

allprojects {

    val kotlinVersion: String by rootProject.extra
    val junitPlatformVersion: String by rootProject.extra
    val junit4Version: String by rootProject.extra
    val junitVintageVersion: String by rootProject.extra
    val junitJupiterVersion: String by rootProject.extra
    val log4jVersion: String by rootProject.extra

    apply {
        plugin("kotlin")
        plugin("jacoco")
        plugin("org.junit.platform.gradle.plugin")
    }

    repositories {
        jcenter()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

        testCompile("org.junit.jupiter:junit-jupiter-api:$junitPlatformVersion")

        // JUnit Jupiter API and TestEngine implementation
        testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

        // If you also want to support JUnit 3 and JUnit 4 tests
        testCompile("junit:junit:$junit4Version")
        testRuntime("org.junit.vintage:junit-vintage-engine:$junitVintageVersion")

        // To use Log4J's LogManager
        testRuntime("org.apache.logging.log4j:log4j-core:$log4jVersion")
        testRuntime("org.apache.logging.log4j:log4j-jul:$log4jVersion")

        testCompile("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
        testCompile("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
        testCompile("com.nhaarman:mockito-kotlin:1.3.0") {
            exclude(group = "org.jetbrains.kotlin")
        }
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
            sourceDirectories = files(sourceSets["main"].allSource.srcDirs)
            classDirectories = files(sourceSets["main"].output)
        }
    }
}