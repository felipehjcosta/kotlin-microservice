dependencies {
    compile(project(":server"))
    compile("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    compile("org.springframework.boot:spring-boot-starter-web:1.5.3.RELEASE")
    testCompile("org.springframework.boot:spring-boot-starter-test:1.5.3.RELEASE")
}