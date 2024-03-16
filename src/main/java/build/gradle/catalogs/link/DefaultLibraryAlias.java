package build.gradle.catalogs.link;

import build.gradle.catalogs.api.LibraryAlias;
import build.gradle.catalogs.api.NoVersionLibraryLink;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;

public class DefaultLibraryAlias extends AbstractAliasLink implements LibraryAlias {

  private final String groupArtifactVersion;

  public static LibraryAlias create(String group, String artifact, String version) {
    return new DefaultLibraryAlias(group + ":" + artifact + ":" + version);
  }

  private DefaultLibraryAlias(String groupArtifactVersion) {
    this.groupArtifactVersion = groupArtifactVersion;
  }

  public static LibraryAlias create(String groupArtifactVersion) {
    return new DefaultLibraryAlias(groupArtifactVersion);
  }

  public static NoVersionLibraryLink create(String group, String artifact) {
    return new NoVersion(group, artifact);
  }

  @Override
  public String getDependencyString() {
    return groupArtifactVersion;
  }

  @Override
  public void register(String alias, VersionCatalogBuilder catalog) {
    catalog.library(alias, groupArtifactVersion);
  }

  private static class NoVersion extends AbstractAliasLink implements NoVersionLibraryLink {

    private final String group;
    private final String artifact;

    public NoVersion(String group, String artifact) {
      this.group = group;
      this.artifact = artifact;
    }

    @NotNull
    @Override
    public LibraryAlias version(@NotNull String version) {
      return create(group + ":" + artifact + ":" + version);
    }

    @Override
    public String getDependencyString() {
      return group + ":" + artifact;
    }

    @Override
    public void register(String alias, VersionCatalogBuilder catalog) {
      catalog.library(alias, group, artifact).withoutVersion();
    }
  } // class NoVersion

} // class DefaultLibraryLink
