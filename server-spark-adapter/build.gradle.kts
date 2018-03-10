val kotlinVersion: String by rootProject.extra

dependencies {
    compile(project(":server"))
    compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    compile("com.sparkjava:spark-core:2.3")
    compile("com.beust:klaxon:0.30")
    compile("com.mitchellbosecke:pebble:2.1.0")
}