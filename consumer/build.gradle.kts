@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java")

    alias(spring.plugins.springBoot)
    alias(spring.plugins.dependencyManagement)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(org.springframework.boot.web)
    implementation(org.springframework.boot.webflux)

    implementation(org.springframework.boot.actuator)

    testImplementation(org.springframework.boot.test)
    testImplementation(io.projectreactor.test)

    testImplementation(platform(org.junit.bom))
    testImplementation(org.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}