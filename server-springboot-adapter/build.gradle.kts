val kotlinVersion: String by rootProject.extra

dependencies {
    compile(project(":server"))
    compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compile("org.springframework.boot:spring-boot-starter-web:1.5.3.RELEASE")
    testCompile("org.springframework.boot:spring-boot-starter-test:1.5.3.RELEASE")
}