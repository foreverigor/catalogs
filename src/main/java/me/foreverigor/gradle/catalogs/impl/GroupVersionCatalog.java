package me.foreverigor.gradle.catalogs.impl;

import me.foreverigor.gradle.catalogs.api.LibraryAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;

public class GroupVersionCatalog extends PrefixVersionCatalog implements VersionCatalog.Group {

  public GroupVersionCatalog(VersionCatalogBuilder catalog, String aliasPrefix) {
    super(catalog, aliasPrefix);
  }

  @NotNull
  @Override
  public LibraryAlias module(@NotNull String alias, @NotNull String name, @NotNull String version) {
    return library(alias, getPrefix(), name, version);
  }

} // class GroupVersionCatalog
