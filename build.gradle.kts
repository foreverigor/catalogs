import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  `java-gradle-plugin`
  // `kotlin-dsl` // don't add this, does stuff to dsl syntax
  kotlin("jvm") version embeddedKotlinVersion
  `version-catalog` // for reference
}

group = "me.foreverigor"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val javaVersion = JavaVersion.VERSION_11

java {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

tasks.withType<JavaCompile> {
  options.compilerArgs.add("-Xlint:unchecked")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = java.targetCompatibility.majorVersion
}

gradlePlugin {
  plugins.register("catalogue-plugin") {
    id = "me.foreverigor.gradle.catalogue"
    implementationClass = "me.foreverigor.gradle.CatalogsPlugin"
  }
}

/* catalog {
  versionCatalog {
    version("kotlin2", "1.0.0")
  }
  versionCatalog {
    version("kotlin", "1.0.0")
    library("alias", "group", "artifact").version("version")
  }
} */

dependencies {
  compileOnly(gradleApi())
  implementation(kotlin("stdlib"))
}
