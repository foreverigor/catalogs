package me.foreverigor.gradle.catalogs.impl;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty1;
import me.foreverigor.gradle.CatalogsPlugin;
import me.foreverigor.gradle.catalogs.DefaultVersions;
import me.foreverigor.gradle.catalogs.api.*;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
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

    @Override
    public @NotNull VersionCatalogBuilder getRealCatalog() {
        return realCatalog;
    }

    @Override
    public @NotNull CatalogGroupProvider catalog(@NotNull String catalogPrefix) {
        return groupConsumer -> groupConsumer.invoke(new GroupVersionCatalog(realCatalog, alias(catalogPrefix)));
    }

    @Override
    public void catalog(@NotNull String catalogPrefix, @NotNull Function1<? super VersionCatalog, Unit> catalogConsumer) {
        catalog(catalogPrefix, (Consumer<VersionCatalog>) catalogConsumer::invoke);
    }

    private void catalog(@NotNull String nestedPrefix, @NotNull Consumer<VersionCatalog> catalogConsumer) {
        catalogConsumer.accept(new PrefixVersionCatalog(realCatalog, alias(nestedPrefix)));
    }

    @Override
    public void group(@NotNull String catalogPrefix, @NotNull Function1<? super Group, Unit> groupConsumer) {
        groupConsumer.invoke(new GroupVersionCatalog(realCatalog, alias(catalogPrefix)));
    }

    @Override
    public @NotNull LibraryAlias library(@NotNull String nestedAlias, @NotNull String group, @NotNull String artifact, @NotNull String version) {
        var libraryAlias = DependencyAlias.forName(alias(nestedAlias))
            .group(group)
            .artifact(artifact)
            .version(version)
            .register(this);
        return log(nestedAlias, libraryAlias);
    }

    @Override
    public @NotNull LibraryAlias library(@NotNull String nestedAlias,
                                         @NotNull String group,
                                         @NotNull String artifact,
                                         @NotNull KProperty1<DefaultVersions, String> version) {
        var alias = DependencyAlias.forName(alias(nestedAlias))
            .group(group)
            .artifact(artifact)
            .version(version)
            .register(this);
        return log(nestedAlias, alias);
    }

    @Override
    @Deprecated
    public @NotNull LibraryAlias library(@NotNull String nestedAlias, @NotNull String groupArtifactVersion) {
        return log(nestedAlias, DependencyAlias.create(groupArtifactVersion).register(alias(nestedAlias), this));
    }
    public @NotNull PluginAlias plugin(@NotNull String nestedAlias, @NotNull String id, @NotNull KProperty1<DefaultVersions, String> version) {
        return log(nestedAlias, DependencyAlias.forName(alias(nestedAlias)).pluginId(id).version(version).register(this));
    }

    @Override
    public @NotNull PluginAlias plugin(@NotNull String nestedAlias, @NotNull String id, @NotNull String version) {
        return log(nestedAlias, DependencyAlias.forName(alias(nestedAlias)).pluginId(id).version(version).register(this));
    }

    @Override
    public void bundle(@NotNull String nestedAlias, @NotNull LibraryAlias... aliases) {
        bundle(nestedAlias, List.of(aliases));
    }

    @Override
    public void bundle(@NotNull String nestedAlias, @NotNull List<? extends LibraryAlias> aliases) {
        List<String> aliasNames = aliases.stream()
            .map(LibraryAlias::getName)
            .map(s -> Objects.requireNonNull(s, "name of alias was null"))
            .toList();
        realCatalog.bundle(alias(nestedAlias), aliasNames);
        log(alias(nestedAlias), "bundle " + aliasNames);
    }

    @Override
    public void library(@NotNull String nestedAlias, @NotNull LibraryAlias libraryLink) {
        log(nestedAlias, libraryLink.register(alias(nestedAlias), this));
    }

    @Override
    public void plugin(@NotNull String nestedAlias, @NotNull PluginAlias pluginLink) {
        log(nestedAlias, pluginLink.register(alias(nestedAlias), this));
    }

    private @NotNull String alias(String nestedAlias) {
        return myPrefix.isEmpty() ? nestedAlias : myPrefix + "." + nestedAlias; // Differentiate between toplevel and nested
    }

    private <T> T log(String nestedAlias, T aliasTarget) {
        if (!(realCatalog instanceof Stub)) {
            logger.info("registered alias '{}' for '{}' in catalog '{}'", alias(nestedAlias), aliasTarget, realCatalog.getName());
        }
        return aliasTarget;
    }

    @Override
    public String toString() {
        return "PrefixVersionCatalog{" + "prefix='" + myPrefix + '\'' + '}';
    }
} // class PrefixVersionCatalog
