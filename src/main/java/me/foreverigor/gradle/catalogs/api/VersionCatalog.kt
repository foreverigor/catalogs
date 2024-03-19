package me.foreverigor.gradle.catalogs.api

import me.foreverigor.gradle.catalogs.DefaultVersions.Companion.withoutVersion
import me.foreverigor.gradle.catalogs.impl.VersionRef
import org.gradle.api.initialization.dsl.VersionCatalogBuilder

/**
 * gradle api equivalent is [org.gradle.api.initialization.dsl.VersionCatalogBuilder]
 * however this one is a lot more flexible
 */
interface VersionCatalog {

    val prefix: String

    val realCatalog: VersionCatalogBuilder
    fun catalog(catalogPrefix: String): CatalogGroupProvider

    fun catalog(catalogPrefix: String, catalogConsumer: VersionCatalog.() -> Unit)

    fun group(catalogPrefix: String, catalogConsumer: Group.() -> Unit)

    fun library(nestedAlias: String, groupArtifactVersion: String): LibraryAlias

    fun library(nestedAlias: String, group: String, artifact: String, version: String = withoutVersion): LibraryAlias

    fun library(nestedAlias: String, group: String, artifact: String, version: VersionRef): LibraryAlias

    @Deprecated("")
    fun plugin(nestedAlias: String, id: String): VersionCatalogBuilder.PluginAliasBuilder

    fun plugin(nestedAlias: String, id: String, version: String): PluginAlias

    fun plugin(nestedAlias: String, id: String, version: VersionRef): PluginAlias

    // Links
    fun library(nestedAlias: String, libraryAlias: LibraryAlias)

    fun plugin(nestedAlias: String, pluginAlias: PluginAlias)

    fun bundle(nestedAlias: String, aliases: List<LibraryAlias>)
    // if you haven't noticed the default gradle api is non-type safe, but this one is. That's because it's better

    interface Group : VersionCatalog {

        /**
         * shortcut form of [library]
         *
         * this part of the DSL is shamelessly stolen from the
         * [refreshVersions](https://github.com/Splitties/refreshVersions/tree/main/plugins/dependencies/src/main/kotlin/dependencies)
         * plugin (see also [Use built-in dependency notations](https://splitties.github.io/refreshVersions/add-dependencies/#use-built-in-dependency-notations))
         */
        fun module(alias: String, name: String, version: VersionRef): LibraryAlias

        fun module(alias: String, name: String, version: String = withoutVersion): LibraryAlias
    }

    interface CatalogGroupProvider {
        fun group(groupConsumer: Group.() -> Unit)
    }

} // interface VersionCatalog

