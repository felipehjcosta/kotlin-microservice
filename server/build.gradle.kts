val kotlinVersion: String by rootProject.extra

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    compile("com.beust:klaxon:0.24")
    compile("com.mitchellbosecke:pebble:2.1.0")
}