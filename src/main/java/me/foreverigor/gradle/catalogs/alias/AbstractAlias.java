package me.foreverigor.gradle.catalogs.alias;

import me.foreverigor.gradle.catalogs.api.DependencyAlias;
import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import me.foreverigor.gradle.catalogs.impl.CatalogVersions;
import me.foreverigor.gradle.catalogs.impl.DepVersion;
import me.foreverigor.gradle.catalogs.impl.DepVersionReference;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractAlias<B extends AliasBuilder<?,?>> implements DependencyAlias {

  protected final String name;
  protected final DepVersion version;

  protected VersionCatalog myCatalog;

  public AbstractAlias(B builder) {
    this.name = Objects.requireNonNull(builder.getName(), "name can't be null");
    this.version = Objects.requireNonNull(builder.getVersion(), "version can't be null");
  }

  protected void maybeRegister(VersionCatalog catalogToRegister) {
    if (catalogToRegister != null) {
      register(name, catalogToRegister);
      this.myCatalog = catalogToRegister;
    }
  }

  String getDependencyString() {
    return "NONAME";
  }

  @Override
  public String toString() {
    return this instanceof LibAlias ? "library " + getDependencyString() : "plugin " + getDependencyString();
  }

  @Override
  public DependencyAlias register(@NotNull String alias, @NotNull VersionCatalog catalog) {
    if (myCatalog != null && name.equals(alias)) {
      return this;
    }
    boolean ref = false;
    if (version instanceof DepVersionReference versionReference) {
      CatalogVersions.INSTANCE.registerVersion(versionReference.getVersion(), catalog);
      ref = true;
    }
    this.doRegister(alias, catalog, ref).accept(version.get());
    return this;
  }

  protected abstract Consumer<String> doRegister(String aliasName, VersionCatalog catalog, boolean isVersionRef);

}
