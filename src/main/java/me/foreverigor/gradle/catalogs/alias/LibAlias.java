package me.foreverigor.gradle.catalogs.alias;

import me.foreverigor.gradle.catalogs.api.LibraryAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import me.foreverigor.gradle.catalogs.impl.DepVersion;

import java.util.function.Consumer;

public class LibAlias extends AbstractAlias<AliasBuilder.LibraryAliasBuilder> implements LibraryAlias {

  private final String group;
  private final String artifact;

  public LibAlias(AliasBuilder.LibraryAliasBuilder builder) {
    this(builder, null);
  }

  public LibAlias(AliasBuilder.LibraryAliasBuilder builder, VersionCatalog catalog) {
    super(builder);
    this.group = builder.getGroup();
    this.artifact = builder.getArtifact();
    maybeRegister(catalog);
  }

  @Override
  String getDependencyString() {
    var groupArtifact = group + ":" + artifact + ":";
    return version instanceof DepVersion.Literal ? groupArtifact + version.get() : groupArtifact + "${" + version.get() + "}";
  }

  @Override
  protected Consumer<String> doRegister(String aliasName, VersionCatalog catalog, boolean isVersionRef) {
    var library = catalog.getRealCatalog().library(aliasName, group, artifact);
    return isVersionRef ? library::versionRef : library::version;
  }
}
