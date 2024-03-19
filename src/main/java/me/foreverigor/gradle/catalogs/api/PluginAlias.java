package me.foreverigor.gradle.catalogs.api;

import me.foreverigor.gradle.catalogs.link.DefaultPluginAlias;
import org.jetbrains.annotations.NotNull;

public interface PluginAlias extends DependencyAlias {

  static PluginAlias create(String pluginId, String version) {
    return new DefaultPluginAlias(pluginId, version);
  }

  @Override
  PluginAlias register(@NotNull String alias, @NotNull VersionCatalog catalog);

  String getId();

  String getVersion();

}
