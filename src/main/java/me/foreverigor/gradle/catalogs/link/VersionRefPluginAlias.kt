package me.foreverigor.gradle.catalogs.link

import me.foreverigor.gradle.catalogs.impl.CatalogVersions
import me.foreverigor.gradle.catalogs.VersionRef
import me.foreverigor.gradle.catalogs.api.PluginAlias
import me.foreverigor.gradle.catalogs.api.VersionCatalog
import me.foreverigor.gradle.catalogs.refName

class VersionRefPluginAlias(private val pluginId: String, private val versionRef: VersionRef) : AbstractAliasLink(), PluginAlias {

    override fun register(alias: String, catalog: VersionCatalog): PluginAlias {
        // Register version, then plugin
        CatalogVersions.registerVersion(versionRef, catalog)
        catalog.realCatalog.plugin(alias, pluginId).versionRef(versionRef.refName)
        return this
    }

    override fun getDependencyString(): String {
        return "$pluginId:\${${versionRef.refName}}"
    }

}
