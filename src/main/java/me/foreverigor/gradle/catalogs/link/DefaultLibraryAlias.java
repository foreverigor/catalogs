package me.foreverigor.gradle.catalogs.link;

import me.foreverigor.gradle.catalogs.api.LibraryAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import org.jetbrains.annotations.NotNull;

public class DefaultLibraryAlias extends AbstractAliasLink implements LibraryAlias {

  private final String groupArtifactVersion;
  private final String group;
  private final String artifact;
  private final String version;

  public static LibraryAlias create(String group, String artifact, String version) {
    return new DefaultLibraryAlias(group, artifact, version);
  }

  private DefaultLibraryAlias(String group, String artifact, String version) {
    this.group = group;
    this.artifact = artifact;
    this.version = version;
    this.groupArtifactVersion = group + ":" + artifact + ":" + version;
  }

  private DefaultLibraryAlias(String groupArtifactVersion) {
    this.groupArtifactVersion = groupArtifactVersion;
    this.group = null;
    this.artifact = null;
    this.version = null;
  }

  public static LibraryAlias create(String groupArtifactVersion) {
    return new DefaultLibraryAlias(groupArtifactVersion);
  }

  public static LibraryAlias.NoVersion create(String group, String artifact) {
    return new NoVersionLibraryAlias(group, artifact);
  }

  @Override
  public LibraryAlias register(@NotNull String alias, @NotNull VersionCatalog catalog) {
    if (artifact != null && group != null && version != null) {
      catalog.getRealCatalog().library(alias, group, artifact).version(version);
    } else {
      catalog.getRealCatalog().library(alias, groupArtifactVersion);
    }
    return this;
  }

  private static class NoVersionLibraryAlias extends AbstractAliasLink implements LibraryAlias.NoVersion {

    private final String group;
    private final String artifact;

    public NoVersionLibraryAlias(String group, String artifact) {
      this.group = group;
      this.artifact = artifact;
    }

    @NotNull
    @Override
    public LibraryAlias version(@NotNull String version) {
      return create(group, artifact, version);
    }

    @Override
    public String toDependencyNotation() {
      return getDependencyString();
    }

    @Override
    public String getDependencyString() {
      return group + ":" + artifact;
    }

    @Override
    public LibraryAlias register(@NotNull String alias, @NotNull VersionCatalog catalog) {
      catalog.getRealCatalog().library(alias, group, artifact).withoutVersion();
      return this;
    }
  } // class NoVersionLibraryAlias

  @Override
  public String getDependencyString() {
    return groupArtifactVersion;
  }

  @Override
  public String toDependencyNotation() {
    return getDependencyString();
  }
} // class DefaultLibraryLink
