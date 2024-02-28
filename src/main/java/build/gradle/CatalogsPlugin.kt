package build.gradle

import build.gradle.catalogs.Catalogs
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

@Suppress("unused", "UnstableApiUsage")
class CatalogsPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        settings.dependencyResolutionManagement {
            Catalogs.catalogsConfig.accept(it.versionCatalogs)
        }
    }
} // class CatalogsPlugin

