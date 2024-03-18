package me.foreverigor.gradle.catalogs.impl;

import me.foreverigor.gradle.CatalogsPlugin;
import me.foreverigor.gradle.catalogs.DefaultVersions;
import me.foreverigor.gradle.catalogs.api.DependencyAlias;
import me.foreverigor.gradle.catalogs.api.LibraryAlias;
import me.foreverigor.gradle.catalogs.api.PluginAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty1;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.function.Consumer;

public class PrefixVersionCatalog implements VersionCatalog {

    private static final Logger logger = CatalogsPlugin.Companion.getLogger();
    private final VersionCatalogBuilder realCatalog;

    protected final String myPrefix;

    public PrefixVersionCatalog(VersionCatalogBuilder catalog, String aliasPrefix) {
        this.realCatalog = catalog;
        this.myPrefix = aliasPrefix;
    }

    @Override
    public @NotNull String getPrefix() {
        return myPrefix.isEmpty() ? realCatalog.getName() : realCatalog.getName() + "." + myPrefix;
    }

    @NotNull
    @Override
    public VersionCatalogBuilder getRealCatalog() {
        return realCatalog;
    }

    @NotNull
    @Override
    public CatalogGroupProvider catalog(@NotNull String catalogPrefix) {
        return groupConsumer -> groupConsumer.invoke(new GroupVersionCatalog(realCatalog, alias(catalogPrefix)));
    }

    @Override
    public void catalog(@NotNull String nestedPrefix, Consumer<VersionCatalog> catalogConsumer) {
        catalogConsumer.accept(new PrefixVersionCatalog(realCatalog, alias(nestedPrefix)));
    }

    @Override
    public void catalog(@NotNull String catalogPrefix, @NotNull Function1<? super VersionCatalog, Unit> catalogConsumer) {
        catalog(catalogPrefix, (Consumer<VersionCatalog>) catalogConsumer::invoke);
    }

    @Override
    public void group(@NotNull String catalogPrefix, @NotNull Function1<? super Group, Unit> groupConsumer) {
        groupConsumer.invoke(new GroupVersionCatalog(realCatalog, alias(catalogPrefix)));
    }

    @NotNull
    private String alias(String nestedAlias) {
        return myPrefix.isEmpty() ? nestedAlias : myPrefix + "." + nestedAlias; // Differentiate between toplevel and nested
    }

    @NotNull
    @Override
    public LibraryAlias library(@NotNull String nestedAlias, @NotNull String group, @NotNull String artifact, @NotNull String version) {
        LibraryAlias alias = version.isEmpty() ? DependencyAlias.create(group, artifact) : DependencyAlias.create(group, artifact, version);
        alias.register(alias(nestedAlias), this);
        return log(nestedAlias, alias);
    }

    @NotNull
    @Override
    public LibraryAlias library(@NotNull String nestedAlias,
                                @NotNull String group,
                                @NotNull String artifact,
                                @NotNull KProperty1<DefaultVersions, String> version) {
        var alias = DependencyAlias.create(group, artifact, version).register(alias(nestedAlias), this);
        return log(nestedAlias, alias);
    }

    @Override
    public @NotNull LibraryAlias library(@NotNull String nestedAlias, @NotNull String groupArtifactVersion) {
        var alias = DependencyAlias.create(groupArtifactVersion).register(alias(nestedAlias), this);
        return log(nestedAlias, alias);
    }

    @Override
    public @NotNull VersionCatalogBuilder.PluginAliasBuilder plugin(@NotNull String nestedAlias, @NotNull String id) {
        return log(nestedAlias, realCatalog.plugin(alias(nestedAlias), id));
    }

    @NotNull
    public PluginAlias plugin(@NotNull String nestedAlias, @NotNull String id, @NotNull KProperty1<DefaultVersions, String> version) {
        return log(nestedAlias, DependencyAlias.create(id, version).register(alias(nestedAlias), this));
    }

    @NotNull
    @Override
    public PluginAlias plugin(@NotNull String nestedAlias, @NotNull String id, @NotNull String version) {
        return log(nestedAlias, PluginAlias.create(id, version).register(alias(nestedAlias), this));
    }

    @Override
    public void plugin(@NotNull String nestedAlias, @NotNull PluginAlias pluginLink) {
        log(nestedAlias, pluginLink.register(alias(nestedAlias), this));
    }

    @Override
    public void library(@NotNull String nestedAlias, @NotNull LibraryAlias libraryLink) {
        log(nestedAlias, libraryLink.register(alias(nestedAlias), this));
    }

    private <T> T log(String nestedAlias, T aliasTarget) {
        if (!(realCatalog instanceof Stub)) {
            logger.info("registered alias '{}' for '{}' in catalog '{}'", alias(nestedAlias), aliasTarget, realCatalog.getName());
        }
        return aliasTarget;
    }

} // class PrefixVersionCatalog
