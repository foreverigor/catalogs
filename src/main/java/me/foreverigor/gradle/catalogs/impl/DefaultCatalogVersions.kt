package me.foreverigor.gradle.catalogs.impl

import me.foreverigor.gradle.CatalogsPlugin
import me.foreverigor.gradle.catalogs.CatalogVersions
import me.foreverigor.gradle.catalogs.api.VersionCatalog
import kotlin.reflect.KProperty1

object DefaultCatalogVersions : CatalogVersions() {

  var versionValues: CatalogVersions = DefaultCatalogVersions
  private val versionRegistrations = mutableMapOf<VersionRef, MutableSet<String>>()

  /**
   * registering the version refs in this roundabout way because have to remember for later in
   * which catalogs the versions were registered
   */
  internal fun registerVersion(version: VersionRef, catalog: VersionCatalog) {
    versionRegistrations.computeIfAbsent(version) { _ -> mutableSetOf() }.let {
      // if (catalog.realCatalogName in it) return // FIXME there is some duplicate calling going on during intellij sync
      // TODO this is related to the settings extension function being inline or not
      val value = resolveValue(version)
      catalog.realCatalog.version(version.refName, value)
      it.add(catalog.realCatalog.name)
      CatalogsPlugin.Logger.debug(
        "registered versionRef '{}' in catalog '{}' with value '{}'",
        version.refName,
        catalog.realCatalog.name,
        value
      )
    }
  } // fun registerVersion(version: VersionRef, catalog: VersionCatalog)

  fun resolveValue(version: VersionRef): String = version.get(versionValues)

} // object DefaultCatalogVersions

typealias VersionRef = KProperty1<CatalogVersions, String> // properties are our version refs

/**
 * because we use versionRefs they can't be uppercase
 *
 * Solution: just make the names lowercase when we use them
 */
val VersionRef.refName // extension property for type alias ? 🤯
  get() = name.lowercase()

fun VersionRef.resolve() = DefaultCatalogVersions.resolveValue(this)
