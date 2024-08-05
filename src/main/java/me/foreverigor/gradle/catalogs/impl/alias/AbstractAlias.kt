package me.foreverigor.gradle.catalogs.impl.alias

import me.foreverigor.gradle.CatalogsPlugin
import me.foreverigor.gradle.catalogs.api.DependencyAlias
import me.foreverigor.gradle.catalogs.api.VersionCatalog
import me.foreverigor.gradle.catalogs.impl.DefaultCatalogVersions
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

  /**
   * This is a map of catalog -> alias in which the alias has been registered
   * It gets created only once and then passed around (sort of a singleton)
   */
  protected val inCatalogs: MutableMap<VersionCatalog, T> by lazy { initMap(catalogMap) }

  protected abstract fun getDependencyString(): String

  protected fun maybeRegister(catalogToRegister: VersionCatalog?) {
    catalogToRegister?.let {
      register(name, it)
      this.myCatalog = it
    }
  }

  fun getInCatalogImpl(catalog: VersionCatalog): T {
    return inCatalogs.computeIfAbsent(catalog, ::registerInCatalog)
  }

  private fun registerInCatalog(catalog: VersionCatalog): T {
    val nameInOtherCatalog = otherCatalogPrefix + name
    createCopy(nameInOtherCatalog).let {
      it.register(nameInOtherCatalog, catalog)
      CatalogsPlugin.Logger.debug("registered linked alias '{}' in catalog '{}'", this, catalog.realCatalog.name)
      return it
    }
  }

  protected abstract fun createCopy(withName: String): T

  private fun initMap(catalogMap: MutableMap<VersionCatalog, T>?): MutableMap<VersionCatalog, T> {
    if (catalogMap != null) return catalogMap
    val map = mutableMapOf<VersionCatalog, T>()
    myCatalog?.let {
      @Suppress("UNCHECKED_CAST")
      map[it] = this as T
    }
    return map
  }

  override fun toString(): String {
    return if (this is DefaultLibraryAlias) "library ${getDependencyString()}" else "plugin ${getDependencyString()}"
  }

  override fun register(alias: String, catalog: VersionCatalog): DependencyAlias {
    if (myCatalog == null || name != alias) {
      var isReference = false
      if (version is DepVersionReference) {
        DefaultCatalogVersions.registerVersion(version.version, catalog)
        isReference = true
      }
      this.doRegister(alias, catalog, isReference).accept(version.get())
    }
    return this
  }

  protected abstract fun doRegister(aliasName: String, catalog: VersionCatalog, isVersionRef: Boolean): Consumer<String>

}
