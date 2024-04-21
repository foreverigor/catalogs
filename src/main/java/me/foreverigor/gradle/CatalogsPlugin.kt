@file:Suppress("unused", "UnstableApiUsage")

package me.foreverigor.gradle

import me.foreverigor.gradle.catalogs.Catalogs
import me.foreverigor.gradle.catalogs.CatalogsPluginExtension
import me.foreverigor.gradle.catalogs.impl.CatalogVersions
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
        settings.extensions.create(CatalogsPluginExtension.NAME, CatalogsPluginExtension.TYPE)
        settings.gradle.settingsEvaluated {
            @Suppress("UNNECESSARY_SAFE_CALL") // Not true!
            settings.extensions.getByType(CatalogsPluginExtension.TYPE).versions?.let {
                Logger.info("will use {} for versions", it::class.qualifiedName)
                CatalogVersions.versionValues = it
            }
            Catalogs.init(settings.dependencyResolutionManagement)
        }
    }
} // class CatalogsPlugin


