import me.foreverigor.gradle.catalogs.gradleCatalogue

rootProject.name = "catalogs-consumer"

pluginManagement {
    includeBuild("../")
}

plugins {
    id("me.foreverigor.gradle.catalogue")
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

apply(plugin = buildGradleKordampPluginId)

dependencyResolutionManagement {
    versionCatalogs {
        create("gradleBuild") {
            plugin("kordamp", "kordamp.plugin").version("latest.release")
        }
    }
}

apply(from = "versions.settins.gradle.kts")
object MyVersions : me.foreverigor.gradle.catalogs.DefaultVersions() {
    override var SpringBoot = SpringBootPlugin
}

gradleCatalogue {
    versions = MyVersions
}