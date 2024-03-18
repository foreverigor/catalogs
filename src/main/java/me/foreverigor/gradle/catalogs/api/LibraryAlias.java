package me.foreverigor.gradle.catalogs.api;

import org.jetbrains.annotations.NotNull;

public interface LibraryAlias extends DependencyAlias {

  @Override
  LibraryAlias register(@NotNull String alias, @NotNull VersionCatalog catalog);

  interface NoVersion extends LibraryAlias {

      @NotNull
      LibraryAlias version(@NotNull String version);

  }
} // interface LibraryAlias
