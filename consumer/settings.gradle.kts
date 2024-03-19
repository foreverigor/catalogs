import me.foreverigor.gradle.catalogs.gradleCatalogue

rootProject.name = "catalogs-consumer"

includeBuild("../")

pluginManagement {
    includeBuild("../")
    repositories {
        gradlePluginPortal()
    }
    plugins {
        // id("me.foreverigor.gradle.catalogue")
    }
}

plugins {
    id("me.foreverigor.gradle.catalogue").apply(true)
    id("org.gradle.toolchains.foojay-resolver-convention") version "latest.release"
}

val kordampPlugin = "org.kordamp.gradle.settings"
// apply(plugin = kordampPlugin + ":0.54.0")

object MyVersions : me.foreverigor.gradle.catalogs.DefaultVersions() {
    override var SpringBoot = SpringBootPlugin
}

gradleCatalogue {
    versions = MyVersions
}