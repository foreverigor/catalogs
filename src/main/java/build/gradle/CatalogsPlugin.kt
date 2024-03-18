@file:Suppress("unused", "UnstableApiUsage")

package build.gradle

import build.gradle.catalogs.Catalogs
import build.gradle.catalogs.CatalogsPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.internal.catalog.DefaultVersionCatalogBuilder
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.PluginAware

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
        settings.extensions.create("catalogCatalogs", CatalogsPluginExtension.TYPE)
        settings.gradle.settingsEvaluated {
            val versions = settings.extensions.getByType(CatalogsPluginExtension.TYPE).versions
            Logger.info("will use {} for versions", versions::class.qualifiedName)
            settings.dependencyResolutionManagement { Catalogs.catalogsConfig.accept(it.versionCatalogs) }
        }
    }
} // class CatalogsPlugin

fun Settings.catalogCatalogs(configure: CatalogsPluginExtension.() -> Unit) {
    extensions.getByType(CatalogsPluginExtension.TYPE).configure()
}


