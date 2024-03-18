import build.gradle.catalogCatalogs
import build.gradle.catalogs.DefaultVersions

rootProject.name = "catalogs-consumer"

pluginManagement {
    includeBuild("../")
    plugins {
        kotlin("jvm") version "1.9.22"
    }
}

plugins {
    id("catalogs-plugin")
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
object MyVersions : DefaultVersions() {
    override var SpringBoot = SpringBootPlugin
}

catalogCatalogs {
    versions = MyVersions
}
