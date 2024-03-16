package build.gradle

import build.gradle.catalogs.Catalogs
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.internal.catalog.DefaultVersionCatalogBuilder
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.PluginAware

@Suppress("unused", "UnstableApiUsage")
class CatalogsPlugin : Plugin<PluginAware> {

    companion object {
        val Logger: Logger = Logging.getLogger(DefaultVersionCatalogBuilder::class.java)
    }
    override fun apply(target: PluginAware) {
        if (target !is Settings) {
            throw UnsupportedOperationException("This is a settings plugin. Please don't try to apply it to something else")
        }
        apply(settings = target)
    }

    private fun apply(settings: Settings) {
        settings.dependencyResolutionManagement { Catalogs.catalogsConfig.accept(it.versionCatalogs) }
    }
} // class CatalogsPlugin

