dependencies {
    compile(project(":server"))
    compile("org.springframework.boot:spring-boot-starter-web:2.0.4.RELEASE")
    testCompile("org.springframework.boot:spring-boot-starter-test:2.0.4.RELEASE")
    testCompile("com.nhaarman:mockito-kotlin:1.3.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
}