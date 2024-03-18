package build.gradle.catalogs.impl

import build.gradle.CatalogsPlugin
import build.gradle.catalogs.DefaultVersions
import build.gradle.catalogs.VersionRef
import build.gradle.catalogs.api.VersionCatalog
import build.gradle.catalogs.refName

object CatalogVersions : DefaultVersions() {

    private var versionValues: DefaultVersions = CatalogVersions
    private val versionRegistrations = mutableMapOf<VersionRef, MutableSet<String>>()

    /**
     * registering the version refs in this roundabout way because have to remember for later in
     * which catalogs the versions were registered
     */
    fun registerVersion(version: VersionRef, catalog: VersionCatalog) {
        versionRegistrations.computeIfAbsent(version) { _ -> mutableSetOf() }.let {
            // if (catalog.realCatalogName in it) return // FIXME there is some duplicate calling going on during intellij sync
            // TODO this is related to the settings extension function being inline or not
            val value = version.get(versionValues)
            catalog.realCatalog.version(version.refName, value)
            it.add(catalog.realCatalog.name)
            CatalogsPlugin.Logger.info("registered versionRef '{}' in catalog '{}' with value '{}'", version.refName, catalog.realCatalog.name, value)
        }
    } // fun registerVersion(version: VersionRef, catalog: VersionCatalog)

} // object CatalogVersions