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

    fun catalog(catalogPrefix: String, catalogConsumer: Consumer<VersionCatalog>)

    fun catalog(catalogPrefix: String, catalogConsumer: VersionCatalog.() -> Unit)

    fun library(nestedAlias: String, groupArtifactVersion: String): LibraryLink

    /**
     * "classic" version
     */
    fun library(nestedAlias: String, group: String, artifact: String): VersionCatalogBuilder.LibraryAliasBuilder

    fun library(nestedAlias: String, group: String, artifact: String, withoutVersion: Boolean = false): HybridLibraryLink

    fun plugin(nestedAlias: String, id: String): VersionCatalogBuilder.PluginAliasBuilder

    fun plugin(nestedAlias: String, id: String, version: String): PluginLink


    val prefix: String
} // interface VersionCatalog

