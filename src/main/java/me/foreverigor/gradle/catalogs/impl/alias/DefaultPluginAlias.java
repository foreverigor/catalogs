package me.foreverigor.gradle.catalogs.impl.alias;

import me.foreverigor.gradle.catalogs.api.PluginAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import me.foreverigor.gradle.catalogs.impl.DepVersion;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DefaultPluginAlias extends AbstractAlias<AliasBuilder.PluginAliasBuilder, PluginAlias> implements PluginAlias {

  private final String pluginId;

  public DefaultPluginAlias(AliasBuilder.PluginAliasBuilder builder, VersionCatalog catalogToRegister) {
    super(builder);
    this.pluginId = builder.getPluginId();
    maybeRegister(catalogToRegister);
  }

  /**
   * copy constructor
   */
  private DefaultPluginAlias(@NotNull String name, DefaultPluginAlias pluginAlias) {
    super(name, pluginAlias.getVersion(), pluginAlias.getInCatalogs());
    this.pluginId = pluginAlias.pluginId;
  }

  @NotNull
  @Override
  protected PluginAlias createCopy(@NotNull String withName) {
    return new DefaultPluginAlias(withName, this);
  }

  @Override
  public PluginAlias getInCatalog(@NotNull VersionCatalog catalog) {
    return super.getInCatalogImpl(catalog);
  }

  @Override
  protected Consumer<String> doRegister(String aliasName, VersionCatalog catalog, boolean isVersionRef) {
    var plugin = catalog.getRealCatalog().plugin(aliasName, pluginId);
    return isVersionRef ? plugin::versionRef : plugin::version;
  }

  @Override
  protected String getDependencyString() {
    return getVersion() instanceof DepVersion.Literal ? pluginId + ":" + getVersion().get() : pluginId + ":${" + getVersion().get() + "}";
  }

} // class PlugAlias
