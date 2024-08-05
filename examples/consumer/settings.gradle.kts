import me.foreverigor.gradle.catalogs.*

rootProject.name = "catalogs-consumer"

pluginManagement {
  includeBuild("../../.")
}

plugins {
  id("me.foreverigor.gradle.catalogue")
}

object MyVersions : CatalogVersions() { init {
  Kotlin = embeddedKotlinVersion
  SpringBootPlugin = "3.3.0"
}}

gradleCatalogue {
  versions = MyVersions
}
