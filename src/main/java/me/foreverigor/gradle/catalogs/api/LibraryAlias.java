package me.foreverigor.gradle.catalogs.api;

import org.gradle.api.Named;
import org.jetbrains.annotations.NotNull;

public interface LibraryAlias extends DependencyAlias, Named {

  @Override
  LibraryAlias register(@NotNull String alias, @NotNull VersionCatalog catalog);

  @Override
  default String getName() {
    return null;
  }

  interface NoVersion extends LibraryAlias {
      @NotNull
      LibraryAlias version(@NotNull String version);
  }


} // interface LibraryAlias
