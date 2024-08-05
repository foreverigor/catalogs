@file:Suppress("unused", "UnstableApiUsage")

package me.foreverigor.gradle

import me.foreverigor.gradle.catalogs.Catalogs
import me.foreverigor.gradle.catalogs.CatalogsPluginExtension
import me.foreverigor.gradle.catalogs.impl.DefaultCatalogVersions
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.PluginAware

class CatalogsPlugin : Plugin<PluginAware> {

  companion object {
    val Logger: Logger = Logging.getLogger(Catalogs::class.java)
  }

  override fun apply(target: PluginAware) {
    if (target !is Settings) {
      throw UnsupportedOperationException("CatalogsPlugin can only be applied in settings")
    }
    apply(target)
  }

  private fun apply(settings: Settings) {
    settings.extensions.create(CatalogsPluginExtension.NAME, CatalogsPluginExtension.TYPE)
    settings.gradle.settingsEvaluated {
      try {
        settings.extensions.getByType(CatalogsPluginExtension.TYPE).run {
          @Suppress("SENSELESS_COMPARISON") // There are some edge cases where null can end up in versions
          if (null == versions || !versionsHasBeenSet) {
            println("Catalog Plugin applied but no versions specified, dependencies will use default versions")
          } else {
            Logger.info("Using {} for versions", versions)
            DefaultCatalogVersions.versionValues = versions
          }
        }
      } catch (e: Exception) { // In case something goes horribly wrong
        Logger.error("Exception occured during CatalogsPlugin extension configuration", e)
      }
      Catalogs.init(settings.dependencyResolutionManagement)
    }
  }
} // class CatalogsPlugin
