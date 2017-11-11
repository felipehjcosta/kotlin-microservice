dependencies {
    compile(project(":server"))
    compile("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    compile("org.jetbrains.kotlin:kotlin-reflect:${rootProject.extra["kotlin_version"]}")
    compile("com.sparkjava:spark-core:2.3")
    compile("com.beust:klaxon:0.30")
    compile("com.mitchellbosecke:pebble:2.1.0")
}