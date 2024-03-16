package build.gradle.catalogs.impl;

import build.gradle.CatalogsPlugin;
import build.gradle.catalogs.api.LibraryAlias;
import build.gradle.catalogs.api.PluginAlias;
import build.gradle.catalogs.api.VersionCatalog;
import build.gradle.catalogs.link.DefaultLibraryAlias;
import build.gradle.catalogs.link.DefaultPluginAlias;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.function.Consumer;

public class PrefixVersionCatalog implements VersionCatalog {

    private static final Logger logger = CatalogsPlugin.Companion.getLogger();
    private final VersionCatalogBuilder realCatalog;
    private final StringBuilder myPrefix;

    public PrefixVersionCatalog(VersionCatalogBuilder catalog, StringBuilder aliasPrefix) {
        this.realCatalog = catalog;
        this.myPrefix = aliasPrefix;
    }

    @Override
    public @NotNull String getPrefix() {
        return realCatalog.getName() + "." + myPrefix;
    }

    @Override
    public void catalog(@NotNull String nestedPrefix, Consumer<VersionCatalog> catalogConsumer) {
        catalogConsumer.accept(new PrefixVersionCatalog(realCatalog, new StringBuilder(alias(nestedPrefix))));
    }

    @Override
    public void catalog(@NotNull String catalogPrefix, @NotNull Function1<? super VersionCatalog, Unit> catalogConsumer) {
        catalog(catalogPrefix, (Consumer<VersionCatalog>) catalogConsumer::invoke);
    }

    @Override
    public @NotNull VersionCatalogBuilder.LibraryAliasBuilder library(@NotNull String nestedAlias, @NotNull String group, @NotNull String artifact) {
        var library = realCatalog.library(alias(nestedAlias), group, artifact);
        logRegistration(nestedAlias, DefaultLibraryAlias.create(group, artifact));
        return library;
    }

    @NotNull
    private String alias(String nestedAlias) {
        return myPrefix.isEmpty() ? nestedAlias : myPrefix + "." + nestedAlias; // Differentiate between toplevel and nested
    }

    @NotNull
    @Override
    public LibraryAlias library(@NotNull String nestedAlias, @NotNull String group, @NotNull String artifact, @NotNull String version) {
        LibraryAlias link;
        if (version.isEmpty()) {
            realCatalog.library(alias(nestedAlias), group, artifact).withoutVersion();
            link = DefaultLibraryAlias.create(group, artifact);
        } else {
            realCatalog.library(alias(nestedAlias), group + ":" + artifact + ":" + version);
            link = DefaultLibraryAlias.create(group, artifact, version);
        }
        logRegistration(nestedAlias, link);
        return link;
    }

    @Override
    public @NotNull LibraryAlias library(@NotNull String nestedAlias, @NotNull String groupArtifactVersion) {
        realCatalog.library(alias(nestedAlias), groupArtifactVersion);
        logRegistration(nestedAlias, "library " + groupArtifactVersion);
        return DefaultLibraryAlias.create(groupArtifactVersion);
    }

    @Override
    public @NotNull VersionCatalogBuilder.PluginAliasBuilder plugin(@NotNull String nestedAlias, @NotNull String id) {
        var plugin = realCatalog.plugin(alias(nestedAlias), id);
        logRegistration(nestedAlias, "plugin " + id);
        return plugin;
    }

    @NotNull
    @Override
    public PluginAlias plugin(@NotNull String nestedAlias, @NotNull String id, @NotNull String version) {
        plugin(nestedAlias, id).version(version);
        return new DefaultPluginAlias(id, version);
    }

    @Override
    public void plugin(@NotNull String nestedAlias, @NotNull PluginAlias pluginLink) {
        pluginLink.register(alias(nestedAlias), realCatalog);
        logRegistration(nestedAlias, pluginLink);
    }

    @Override
    public void library(@NotNull String nestedAlias, @NotNull LibraryAlias libraryLink) {
        libraryLink.register(alias(nestedAlias), realCatalog);
        logRegistration(nestedAlias, libraryLink);
    }

    private void logRegistration(String nestedAlias, Object aliasTarget) {
        logger.info("registered alias {} for {} in catalog {}", alias(nestedAlias), aliasTarget, realCatalog.getName());
    }

} // class PrefixVersionCatalog
