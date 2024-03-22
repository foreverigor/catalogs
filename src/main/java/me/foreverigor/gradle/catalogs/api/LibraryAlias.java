package me.foreverigor.gradle.catalogs.api;

import org.jetbrains.annotations.NotNull;

public interface LibraryAlias extends DependencyAlias {

  @Override
  LibraryAlias getInCatalog(@NotNull VersionCatalog catalog);

  String getName();

} // interface LibraryAlias
