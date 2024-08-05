plugins {
  java
  alias(org.plugins.jetbrains.kotlin.jvm)

  alias(spring.plugins.springBoot)
  alias(spring.plugins.dependencyManagement)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  gradlePluginPortal()
}

java {
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  implementation(org.springframework.boot.web)
  implementation(org.springframework.boot.webflux)

  implementation(org.springframework.boot.actuator)

  testImplementation(org.springframework.boot.test)
  testImplementation(io.projectreactor.test)

  testImplementation(platform(org.junit.junit5Bom))
  testImplementation(org.junit.jupiter)
  testRuntimeOnly(com.h2database.h2)
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions.jvmTarget = java.targetCompatibility.majorVersion
}
