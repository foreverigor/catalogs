package me.foreverigor.gradle.catalogs.link;

import me.foreverigor.gradle.catalogs.api.LibraryAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import org.jetbrains.annotations.NotNull;

public class GavLibraryAlias extends AbstractAliasLink implements LibraryAlias {

  private final String groupArtifactVersion;

  public GavLibraryAlias(String groupArtifactVersion) {
    this.groupArtifactVersion = groupArtifactVersion;
  }

  @Override
  public LibraryAlias register(@NotNull String alias, @NotNull VersionCatalog catalog) {
    catalog.getRealCatalog().library(alias, groupArtifactVersion);
    rememberName(alias);
    return this;
  }

  @Override
  public LibraryAlias getInCatalog(@NotNull VersionCatalog catalog) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getName() {
    return normalizedName();
  }

  @Override
  public String getDependencyString() {
    return groupArtifactVersion;
  }

} // class DefaultLibraryLink
