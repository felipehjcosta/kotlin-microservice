buildscript {
    extra["kotlin_version"] = "1.2.21"

    extra["junit4Version"] = "4.12"
    extra["junitPlatformVersion"] = "1.0.3"
    extra["junitVintageVersion"] = "4.12.3"
    extra["junitJupiterVersion"] = "5.0.3"
    extra["log4jVersion"] = "2.9.0"

    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${extra["kotlin_version"]}")
        classpath("org.junit.platform:junit-platform-gradle-plugin:${extra["junitPlatformVersion"]}")
    }
}

plugins {
    base
    jacoco
    java
}

allprojects {
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
        testCompile("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junitJupiterVersion"]}")

        //        // JUnit Jupiter API and TestEngine implementation
        testCompile("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junitJupiterVersion"]}")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junitJupiterVersion"]}")

        // If you also want to support JUnit 3 and JUnit 4 tests
        testCompile("junit:junit:${rootProject.extra["junit4Version"]}")
        testRuntime("org.junit.vintage:junit-vintage-engine:${rootProject.extra["junitVintageVersion"]}")

        // To use Log4J's LogManager
        testRuntime("org.apache.logging.log4j:log4j-core:${rootProject.extra["log4jVersion"]}")
        testRuntime("org.apache.logging.log4j:log4j-jul:${rootProject.extra["log4jVersion"]}")

        testCompile("org.jetbrains.kotlin:kotlin-test:${rootProject.extra["kotlin_version"]}")
        testCompile("org.jetbrains.kotlin:kotlin-test-junit:${rootProject.extra["kotlin_version"]}")
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
            sourceSets(java.sourceSets["main"])
            sourceDirectories = files(java.sourceSets["main"].allSource.srcDirs)
            classDirectories = files(java.sourceSets["main"].output)
        }
    }
}