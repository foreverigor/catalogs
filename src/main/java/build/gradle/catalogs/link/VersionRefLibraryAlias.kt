package build.gradle.catalogs.link

import build.gradle.catalogs.impl.CatalogVersions
import build.gradle.catalogs.VersionRef
import build.gradle.catalogs.api.LibraryAlias
import build.gradle.catalogs.api.VersionCatalog
import build.gradle.catalogs.refName

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
