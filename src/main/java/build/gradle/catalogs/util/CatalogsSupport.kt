package build.gradle.catalogs.util

import build.gradle.catalogs.api.LibraryAlias
import build.gradle.catalogs.api.PluginAlias
import build.gradle.catalogs.api.VersionCatalogContainer
import build.gradle.catalogs.api.VersionCatalogsConfiguration
import build.gradle.catalogs.link.DependencyLinks

open class CatalogsSupport  {

    protected val libs = DependencyLinks<LibraryAlias>()
    protected val plugins = DependencyLinks<PluginAlias>()
    protected fun catalogs(name: String = "unnamed", conf: VersionCatalogContainer.() -> Unit): VersionCatalogsConfiguration {
        return object : VersionCatalogsConfiguration {
            override fun configure(it: VersionCatalogContainer) {
                conf.invoke(it)
            }

            override fun getName(): String {
                return name
            }
        }
    }

}