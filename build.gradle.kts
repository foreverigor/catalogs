plugins {
    java
    `java-gradle-plugin`
    // `kotlin-dsl` // don't add this, does stuff to syntax
    kotlin("jvm") version embeddedKotlinVersion
    `version-catalog` // for reference
}

group = "me.foreverigor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins.register("catalogue-plugin") {
        id = "me.foreverigor.gradle.catalogue"
        implementationClass = "me.foreverigor.gradle.CatalogsPlugin"
    }
}

catalog {
    versionCatalog {
        version("kotlin2", "1.0.0")
    }
    versionCatalog {
        version("kotlin", "1.0.0")
        // add dependencies and aliases here
        library("alias", "group", "artifact").version("version")
//        alias("<alias name>").to("<group-id>", "<artifact-id>").version("<version code>")
    }
}

dependencies {
    testImplementation("junit", "junit", "4.12")
    implementation(kotlin("stdlib"))
}
