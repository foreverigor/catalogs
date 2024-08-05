package me.foreverigor.gradle.catalogs.api

import java.util.function.Consumer

/**
 * gradle api equivalent is [org.gradle.api.initialization.resolve.MutableVersionCatalogContainer]
 */
interface VersionCatalogContainer {

  fun catalog(catalogName: String)

  fun catalog(catalogName: String, catalogConsumer: VersionCatalog.() -> Unit)

  fun catalog(catalogName: String, catalogConsumer: Consumer<VersionCatalog>)

} // interface VersionCatalogBuilder

