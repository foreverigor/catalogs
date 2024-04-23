package me.foreverigor.gradle.catalogs.alias

import me.foreverigor.gradle.CatalogsPlugin
import me.foreverigor.gradle.catalogs.api.DependencyAlias
import me.foreverigor.gradle.catalogs.api.VersionCatalog
import me.foreverigor.gradle.catalogs.impl.CatalogVersions
import me.foreverigor.gradle.catalogs.impl.DepVersion
import me.foreverigor.gradle.catalogs.impl.DepVersionReference
import java.util.function.Consumer

abstract class AbstractAlias<B : AliasBuilder<T, *>, T : DependencyAlias> constructor(
  val name: String,
  protected val version: DepVersion,
  private val catalogMap: MutableMap<VersionCatalog, T>? = null
) : DependencyAlias {

  constructor(builder: B) : this(builder.name, builder.version)

  companion object {
    const val otherCatalogPrefix = "links."
  }

  private var myCatalog: VersionCatalog? = null
  protected val inCatalogs: MutableMap<VersionCatalog, T> by lazy { initMap(catalogMap) }

  protected abstract fun getDependencyString(): String

  protected fun maybeRegister(catalogToRegister: VersionCatalog?) {
    if (catalogToRegister != null) {
      register(name, catalogToRegister)
      this.myCatalog = catalogToRegister
    }
  }

  fun getInCatalogImpl(catalog: VersionCatalog): T {
    return inCatalogs.computeIfAbsent(catalog, ::registerInCatalog)
  }

  private fun registerInCatalog(catalog: VersionCatalog): T {
    val nameInOtherCatalog = otherCatalogPrefix + name
    val copy = createCopy(nameInOtherCatalog)
    copy.register(nameInOtherCatalog, catalog)
    CatalogsPlugin.Logger.info("registered linked alias '{}' in catalog '{}'", this, catalog.realCatalog.name)
    return copy
  }

  protected abstract fun createCopy(withName: String): T

  private fun initMap(catalogMap: MutableMap<VersionCatalog, T>?): MutableMap<VersionCatalog, T> {
    if (catalogMap != null) return catalogMap
    val map = mutableMapOf<VersionCatalog, T>()
    val regCatalog = myCatalog
    if (regCatalog != null) {
      map[regCatalog] = this as T
    }
    return map
  }

  override fun toString(): String {
    return if (this is DefaultLibraryAlias) "library ${getDependencyString()}" else "plugin ${getDependencyString()}"
  }

  override fun register(alias: String, catalog: VersionCatalog): DependencyAlias {
    if (myCatalog != null && name == alias) {
      return this
    }
    var ref = false
    if (version is DepVersionReference) {
      CatalogVersions.registerVersion(version.version, catalog)
      ref = true
    }
    this.doRegister(alias, catalog, ref).accept(version.get())
    return this
  }

  protected abstract fun doRegister(aliasName: String, catalog: VersionCatalog, isVersionRef: Boolean): Consumer<String>

}
