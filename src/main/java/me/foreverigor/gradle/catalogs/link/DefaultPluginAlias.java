package me.foreverigor.gradle.catalogs.link;

import me.foreverigor.gradle.catalogs.api.PluginAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import org.jetbrains.annotations.NotNull;

public class DefaultPluginAlias extends AbstractAliasLink implements PluginAlias {
  private final String pluginId;
  private final String version;

  public DefaultPluginAlias(String id, String version) {
    this.pluginId = id;
    this.version = version;
  }

  @Override
  public PluginAlias register(@NotNull String alias, @NotNull VersionCatalog catalog) {
    catalog.getRealCatalog().plugin(alias, pluginId).version(version);
    return this;
  }

  @Override
  public String getDependencyString() {
    return pluginId + " version " + version;
  }
}
