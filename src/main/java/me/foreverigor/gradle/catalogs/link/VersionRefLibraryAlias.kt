package me.foreverigor.gradle.catalogs.link

import me.foreverigor.gradle.catalogs.impl.CatalogVersions
import me.foreverigor.gradle.catalogs.VersionRef
import me.foreverigor.gradle.catalogs.api.LibraryAlias
import me.foreverigor.gradle.catalogs.api.VersionCatalog
import me.foreverigor.gradle.catalogs.refName

class VersionRefLibraryAlias(private val group: String,
                             private val artifact: String,
                             private val versionRef: VersionRef) : AbstractAliasLink(), LibraryAlias { // class VersionRefLibraryAlias
    /**
     * not valid dependency notation
     */
    override fun getDependencyString(): String {
        return "$group:$artifact:\${${versionRef.refName}}"
    }

    override fun register(alias: String, catalog: VersionCatalog): LibraryAlias {
        // Register both the version and dependency:
        CatalogVersions.registerVersion(versionRef, catalog)
        catalog.realCatalog.library(alias, group, artifact).versionRef(versionRef.refName)
        return this
    }
}
