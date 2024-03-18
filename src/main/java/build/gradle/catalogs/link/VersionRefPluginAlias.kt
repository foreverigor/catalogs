package build.gradle.catalogs.link

import build.gradle.catalogs.impl.CatalogVersions
import build.gradle.catalogs.VersionRef
import build.gradle.catalogs.api.PluginAlias
import build.gradle.catalogs.api.VersionCatalog
import build.gradle.catalogs.refName

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
