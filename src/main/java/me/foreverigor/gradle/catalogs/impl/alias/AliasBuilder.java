package me.foreverigor.gradle.catalogs.impl.alias;

import kotlin.reflect.KProperty1;
import me.foreverigor.gradle.catalogs.CatalogVersions;
import me.foreverigor.gradle.catalogs.api.*;
import me.foreverigor.gradle.catalogs.impl.DefaultCatalogVersions;
import me.foreverigor.gradle.catalogs.impl.DepVersion;

public interface AliasBuilder<T extends DependencyAlias, Self extends AliasBuilder<T, Self>> {

  Self version(String version);
  Self version(KProperty1<CatalogVersions, String> version);

  T register(VersionCatalog catalog);

  String getName();

  DepVersion getVersion();

  abstract class AbstractAliasBuilder<T extends DependencyAlias, Self extends AliasBuilder<T, Self>> implements AliasBuilder<T, Self> {

    protected final String name;
    protected DepVersion version;

    public AbstractAliasBuilder(String name, DepVersion version) {
      this.name = name;
      this.version = version;
    }

    public AbstractAliasBuilder(String name) {
      this.name = name;
    }

    @Override
    public Self version(String version) {
      this.version = DepVersion.fromLiteral(version);
      return self();
    }

    @Override
    public Self version(KProperty1<CatalogVersions, String> version) {
      this.version = DepVersion.fromRef(version);
      return self();
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public DepVersion getVersion() {
      return version;
    }

    @SuppressWarnings("unchecked")
    protected Self self() {
      return (Self) this;
    }
  } // class AbstractAliasBuilder

  class GenericAliasBuilder extends AbstractAliasBuilder<DependencyAlias, GenericAliasBuilder> {

    public GenericAliasBuilder(String name) {
      super(name);
    }

    public PluginAliasBuilder pluginId(String id) {
      return new PluginAliasBuilder(name, version).pluginId(id);
    }

    public LibraryAliasBuilder group(String group) {
      return new LibraryAliasBuilder(name, version).group(group);
    }

    public LibraryAliasBuilder artifact(String artifact) {
      return new LibraryAliasBuilder(name, version).artifact(artifact);
    }

    @Override
    public DependencyAlias register(VersionCatalog catalog) {
      throw new UnsupportedOperationException();
    }
  }

  class PluginAliasBuilder extends AbstractAliasBuilder<PluginAlias, PluginAliasBuilder> {

    public PluginAliasBuilder(String name, DepVersion version) {
      super(name, version);
    }

    private String pluginId = "";
    public PluginAliasBuilder pluginId(String id) {
      this.pluginId = id;
      return self();
    }

    @Override
    public PluginAlias register(VersionCatalog catalog) {
      return new DefaultPluginAlias(this, catalog);
    }

    String getPluginId() {
      return pluginId;
    }
  } // class PluginAliasBuilder

  class LibraryAliasBuilder extends AbstractAliasBuilder<LibraryAlias, LibraryAliasBuilder> {

    public LibraryAliasBuilder(String name, DepVersion version) {
      super(name, version);
    }

    private String group = "";
    private String artifact = "";

    public LibraryAliasBuilder group(String group) {
      this.group = group;
      return self();
    }

    public LibraryAliasBuilder artifact(String artifact) {
      this.artifact = artifact;
      return self();
    }

    public LibraryAliasBuilder withoutVersion() {
      return version(DefaultCatalogVersions.withoutVersion);
    }

    @Override
    public LibraryAlias register(VersionCatalog catalog) {
      return new DefaultLibraryAlias(this, catalog);
    }

    String getGroup() {
      return group;
    }

    String getArtifact() {
      return artifact;
    }
  } // class LibraryAliasBuilder

} // interface AliasBuilder
