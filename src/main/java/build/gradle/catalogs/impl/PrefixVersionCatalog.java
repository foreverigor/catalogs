package build.gradle.catalogs.impl;

import build.gradle.catalogs.api.NoVersionLibraryLink;
import build.gradle.catalogs.api.LibraryLink;
import build.gradle.catalogs.api.PluginLink;
import build.gradle.catalogs.api.VersionCatalog;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PrefixVersionCatalog implements VersionCatalog {
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
        catalogConsumer.accept(new PrefixVersionCatalog(realCatalog, new StringBuilder(myPrefix).append(nestedPrefix)));
    }

    @Override
    public void catalog(@NotNull String catalogPrefix, @NotNull Function1<? super VersionCatalog, Unit> catalogConsumer) {
        catalog(catalogPrefix, (Consumer<VersionCatalog>) catalogConsumer::invoke);
    }

    @Override
    public @NotNull VersionCatalogBuilder.LibraryAliasBuilder library(@NotNull String nestedAlias, @NotNull String group, @NotNull String artifact) {
        System.out.println("registered library alias " + alias(nestedAlias) + " in catalog " + realCatalog.getName());
        return realCatalog.library(alias(nestedAlias), group, artifact);
    }

    @NotNull
    @Override
    public NoVersionLibraryLink library(@NotNull String nestedAlias, @NotNull String group, @NotNull String artifact, boolean withoutVersion) {
        if (!withoutVersion) {
            realCatalog.library(alias(nestedAlias), artifact); // Will fail, interface allows such a definition, doesn't mean it's going to give a meaningful result
        } else { // Actual reason this method exists:
            realCatalog.library(alias(nestedAlias), group, artifact).withoutVersion();
        }
        LibraryLink idRef = new LibraryLink() {};
        return version -> idRef;
    }

    @NotNull
    private String alias(String nestedAlias) {
        return myPrefix + "." + nestedAlias;
    }

    @Override
    public @NotNull LibraryLink library(@NotNull String nestedAlias, @NotNull String groupArtifactVersion) {
        System.out.println("registered library alias " + alias(nestedAlias) + " in catalog " + realCatalog.getName());
        realCatalog.library(alias(nestedAlias), groupArtifactVersion);
        return new LibraryLink() {};
    }

    @Override
    public @NotNull VersionCatalogBuilder.PluginAliasBuilder plugin(@NotNull String nestedAlias, @NotNull String id) {
        System.out.println("registered plugin alias " + alias(nestedAlias) + " in catalog " + realCatalog.getName());
        return realCatalog.plugin(alias(nestedAlias), id);
    }

    @NotNull
    @Override
    public PluginLink plugin(@NotNull String nestedAlias, @NotNull String id, @NotNull String version) {
        plugin(nestedAlias, id).version(version);
        return new PluginLink() {};
    }

    @Override
    public void plugin(@NotNull String nestedAlias, @NotNull PluginLink pluginLink) {

    }

    @Override
    public void library(@NotNull String nestedAlias, @NotNull LibraryLink libraryLink) {

    }

} // class PrefixVersionCatalog
