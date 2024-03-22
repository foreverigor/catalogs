package me.foreverigor.gradle.catalogs.alias;

import me.foreverigor.gradle.catalogs.api.PluginAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import me.foreverigor.gradle.catalogs.impl.DepVersion;

import java.util.function.Consumer;

public class PlugAlias extends AbstractAlias<AliasBuilder.PluginAliasBuilder> implements PluginAlias {

  private final String pluginId;

  public PlugAlias(AliasBuilder.PluginAliasBuilder builder) {
    this(builder, null);
  }

  public PlugAlias(AliasBuilder.PluginAliasBuilder builder, VersionCatalog catalogToRegister) {
    super(builder);
    this.pluginId = builder.getPluginId();
    maybeRegister(catalogToRegister);
  }

  @Override
  protected Consumer<String> doRegister(String aliasName, VersionCatalog catalog, boolean isVersionRef) {
    var plugin = catalog.getRealCatalog().plugin(aliasName, pluginId);
    return isVersionRef ? plugin::versionRef : plugin::version;
  }

  @Override
  String getDependencyString() {
    return version instanceof DepVersion.Literal ? pluginId + ":" + version.get() : pluginId + ":${" + version.get() + "}";
  }

} // class PlugAlias
