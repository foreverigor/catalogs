package me.foreverigor.gradle.catalogs.impl.alias;

import me.foreverigor.gradle.catalogs.api.LibraryAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import me.foreverigor.gradle.catalogs.impl.DepVersion;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DefaultLibraryAlias extends AbstractAlias<AliasBuilder.LibraryAliasBuilder, LibraryAlias> implements LibraryAlias {

  private final String group;
  private final String artifact;

  public DefaultLibraryAlias(String name, DefaultLibraryAlias libraryAlias) {
    super(name, libraryAlias.getVersion(), libraryAlias.getInCatalogs());
    this.group = libraryAlias.group;
    this.artifact = libraryAlias.artifact;
  }

  public DefaultLibraryAlias(AliasBuilder.LibraryAliasBuilder builder, VersionCatalog catalog) {
    super(builder);
    this.group = builder.getGroup();
    this.artifact = builder.getArtifact();
    maybeRegister(catalog);
  }

  @NotNull
  @Override
  protected LibraryAlias createCopy(@NotNull String withName) {
    return new DefaultLibraryAlias(withName, this);
  }

  @Override
  public LibraryAlias getInCatalog(@NotNull VersionCatalog catalog) {
    return super.getInCatalogImpl(catalog);
  }

  @Override
  protected Consumer<String> doRegister(String aliasName, VersionCatalog catalog, boolean isVersionRef) {
    var library = catalog.getRealCatalog().library(aliasName, group, artifact);
    return isVersionRef ? library::versionRef : library::version;
  }

  @Override
  protected String getDependencyString() {
    var groupArtifact = group + ":" + artifact + ":";
    return getVersion() instanceof DepVersion.Literal ? groupArtifact + getVersion().get() : groupArtifact + "${" + getVersion().get() + "}";
  }
}
