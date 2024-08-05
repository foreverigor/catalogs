package me.foreverigor.gradle.catalogs

import me.foreverigor.gradle.catalogs.impl.DefaultCatalogVersions
import org.gradle.api.initialization.Settings

open class CatalogsPluginExtension {

  var versions: CatalogVersions = DefaultCatalogVersions
    set(value) {
      field = value
      versionsHasBeenSet = true
    }

  internal var versionsHasBeenSet: Boolean = false

  companion object {
    val NAME = Settings::gradleCatalogue.name
    val TYPE = CatalogsPluginExtension::class.java
  }

} // class CatalogsPluginExtension

fun Settings.gradleCatalogue(configure: CatalogsPluginExtension.() -> Unit) =
  extensions.getByType(CatalogsPluginExtension.TYPE).configure()
