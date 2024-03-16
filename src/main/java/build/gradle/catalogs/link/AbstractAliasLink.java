package build.gradle.catalogs.link;

import build.gradle.catalogs.api.DependencyAlias;
import build.gradle.catalogs.api.LibraryAlias;

public abstract class AbstractAliasLink implements DependencyAlias {

  public abstract String getDependencyString();

  @Override
  public String toString() {
    return this instanceof LibraryAlias ? "library " + getDependencyString() : "plugin " + getDependencyString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractAliasLink that)) return false;

    return getDependencyString().equals(that.getDependencyString());
  }

  @Override
  public int hashCode() {
    return getDependencyString().hashCode();
  }
}
