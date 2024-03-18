package me.foreverigor.gradle.catalogs.util

import me.foreverigor.gradle.catalogs.api.LibraryAlias
import me.foreverigor.gradle.catalogs.api.PluginAlias
import me.foreverigor.gradle.catalogs.api.VersionCatalogContainer
import me.foreverigor.gradle.catalogs.api.VersionCatalogsConfiguration
import me.foreverigor.gradle.catalogs.link.DependencyLinks

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