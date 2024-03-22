package me.foreverigor.gradle.catalogs.api;

import org.jetbrains.annotations.NotNull;

public interface PluginAlias extends DependencyAlias {

  @Override
  PluginAlias getInCatalog(@NotNull VersionCatalog catalog);
} // interface PluginAlias
