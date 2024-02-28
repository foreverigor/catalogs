plugins {
    java
    `java-gradle-plugin`
    kotlin("jvm") version embeddedKotlinVersion
}

group = "me.foreverigor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins.register("catalogs-plugin") {
        id = "catalogs-plugin"
        implementationClass = "build.gradle.CatalogsPlugin"
    }
}

dependencies {
    testImplementation("junit", "junit", "4.12")
    implementation(kotlin("stdlib"))
}
