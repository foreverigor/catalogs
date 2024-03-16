package build.gradle.catalogs.api

import org.gradle.api.initialization.dsl.VersionCatalogBuilder
import java.util.function.Consumer

/**
 * gradle api equivalent is [org.gradle.api.initialization.dsl.VersionCatalogBuilder]
 */
interface VersionCatalog {
    /**
     * stub method, atm doesn't do anything
     */
//    @JvmDefaultWithoutCompatibility
//    fun catalog(catalogPrefix: String) {}

    val prefix: String

    fun catalog(catalogPrefix: String, catalogConsumer: Consumer<VersionCatalog>)

    fun catalog(catalogPrefix: String, catalogConsumer: VersionCatalog.() -> Unit)

    fun library(nestedAlias: String, groupArtifactVersion: String): LibraryAlias

    fun library(nestedAlias: String, group: String, artifact: String, version: String = ""): LibraryAlias

    /**
     * "classic" version
     */
    fun library(nestedAlias: String, group: String, artifact: String): VersionCatalogBuilder.LibraryAliasBuilder

    fun plugin(nestedAlias: String, id: String): VersionCatalogBuilder.PluginAliasBuilder

    fun plugin(nestedAlias: String, id: String, version: String): PluginAlias

    // Links
    fun library(nestedAlias: String, libraryAlias: LibraryAlias)

    fun plugin(nestedAlias: String, pluginAlias: PluginAlias)

} // interface VersionCatalog

