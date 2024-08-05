package me.foreverigor.gradle.catalogs.util

import me.foreverigor.gradle.catalogs.api.LibraryAlias
import me.foreverigor.gradle.catalogs.api.PluginAlias
import me.foreverigor.gradle.catalogs.api.VersionCatalogContainer
import me.foreverigor.gradle.catalogs.api.VersionCatalogsConfiguration
import me.foreverigor.gradle.catalogs.link.AliasLinks
import org.gradle.api.initialization.resolve.DependencyResolutionManagement

@Suppress("UnstableApiUsage")
abstract class CatalogsSupport {

  protected val libs = AliasLinks<LibraryAlias>()
  protected val plugins = AliasLinks<PluginAlias>()

  protected abstract val catalogsConfigs: List<VersionCatalogsConfiguration>
  protected fun catalogs(
    name: String = "unnamed",
    conf: VersionCatalogContainer.() -> Unit
  ): VersionCatalogsConfiguration {
    return object : VersionCatalogsConfiguration {
      override fun configure(it: VersionCatalogContainer) {
        conf.invoke(it)
      }

      override fun getName(): String {
        return name
      }
    }
  }

  interface InitMarker

  @Volatile
  private var stubInit: InitMarker? = null
  @Volatile
  private var realInit: InitMarker? = null
  @Volatile
  private var inInit = false

  private fun doRealInit(depManagement: DependencyResolutionManagement) {
    catalogsConfigs.forEach {
      it.configure { name, config -> depManagement.versionCatalogs.create(name, config) }
    }
    this.realInit = object : InitMarker {}
  }

  private fun doStubInit() {
    if (null != realInit) {
      this.stubInit = realInit
      return
    }
    catalogsConfigs.forEach {
      it.configure { t, u -> Stubs.createStubCatalog(t, u) }
    }
    this.stubInit = object : InitMarker {}
  }

  fun init(depManagement: DependencyResolutionManagement?) {
    if (inInit) return else inInit = true
    try {
      if (depManagement == null) doStubInit() else doRealInit(depManagement)
    } finally {
      inInit = false
    }
  }

  fun isUnititialized() = stubInit == null && realInit == null
}
