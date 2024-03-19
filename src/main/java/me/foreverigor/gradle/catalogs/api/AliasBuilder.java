package me.foreverigor.gradle.catalogs.api;

import me.foreverigor.gradle.catalogs.link.DefaultLibraryAlias;
import me.foreverigor.gradle.catalogs.link.DefaultPluginAlias;

public interface AliasBuilder<T extends DependencyAlias, Self extends AliasBuilder<T, Self>> {

  Self version(String version);

  default T build() {
    throw new IllegalArgumentException();
  }

  abstract class AbstractAliasBuilder<T extends DependencyAlias, Self extends AliasBuilder<T, Self>> implements AliasBuilder<T, Self> {

    protected String version;

    @Override
    public Self version(String version) {
      this.version = version;
      return self();
    }

    protected Self self() {
      //noinspection unchecked
      return (Self) this;
    }
  }

  class GenericAliasBuilder extends AbstractAliasBuilder<DependencyAlias, GenericAliasBuilder> {
    public BundleAliasBuilder aliases(LibraryAlias... aliases) {
      return new BundleAliasBuilder().version(version).aliases(aliases);
    }

    public PluginAliasBuilder pluginId(String id) {
      return new PluginAliasBuilder().version(version).pluginId(id);
    }

    public LibraryAliasBuilder group(String group) {
      return new LibraryAliasBuilder().version(version).group(group);
    }

    public LibraryAliasBuilder artifact(String artifact) {
      return new LibraryAliasBuilder().version(version).artifact(artifact);
    }
  }

  class BundleAliasBuilder extends AbstractAliasBuilder<BundleAlias, BundleAliasBuilder> {

    private BundleAliasBuilder() {}
    public BundleAliasBuilder aliases(LibraryAlias... aliases) {
      return self();
    }
    @Override
    public BundleAlias build() {
      return super.build();
    }
  }

  class PluginAliasBuilder extends AbstractAliasBuilder<PluginAlias, PluginAliasBuilder> {

    private PluginAliasBuilder() {}
    private String pluginId = "";
    public PluginAliasBuilder pluginId(String id) {
      this.pluginId = id;
      return self();
    }

    @Override
    public PluginAlias build() {
      return new DefaultPluginAlias(pluginId, version);
    }
  }

  class LibraryAliasBuilder extends AbstractAliasBuilder<LibraryAlias, LibraryAliasBuilder> {

    private LibraryAliasBuilder() {}
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

    @Override
    public LibraryAlias build() {
      return DefaultLibraryAlias.create(group, artifact, version);
    }
  }

} // interface AliasBuilder
