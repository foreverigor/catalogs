package me.foreverigor.gradle.catalogs.api;

import me.foreverigor.gradle.catalogs.impl.alias.AliasBuilder;
import me.foreverigor.gradle.catalogs.link.GavLibraryAlias;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * an abstract alias that can be registered with {@link VersionCatalogBuilder}
 */
public interface DependencyAlias {

  DependencyAlias register(@NotNull String alias, @NotNull VersionCatalog catalog);

  DependencyAlias getInCatalog(@NotNull VersionCatalog catalog);
  static AliasBuilder.GenericAliasBuilder forName(String name) {
    return new AliasBuilder.GenericAliasBuilder(name);
  }

  // Factory methods:
  static GavLibraryAlias create(String groupArtifactVersion) {
    return new GavLibraryAlias(groupArtifactVersion);
  }

} // interface DependencyAlias
