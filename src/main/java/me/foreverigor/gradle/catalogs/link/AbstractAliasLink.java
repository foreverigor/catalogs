package me.foreverigor.gradle.catalogs.link;

import me.foreverigor.gradle.catalogs.api.DependencyAlias;
import me.foreverigor.gradle.catalogs.api.LibraryAlias;

import java.util.regex.Pattern;

public abstract class AbstractAliasLink implements DependencyAlias {

  public abstract String getDependencyString();

  protected String registeredName;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractAliasLink)) return false;

    return getDependencyString().equals(((AbstractAliasLink) o).getDependencyString());
  }

  @Override
  public int hashCode() {
    return getDependencyString().hashCode();
  }

  /**
   * {@link org.gradle.api.internal.catalog.AliasNormalizer}
   */
  protected String normalizedName() {
    if (registeredName == null) return null;
    return SEPARATOR.matcher(registeredName).replaceAll(".");
  }

  private final static Pattern SEPARATOR = Pattern.compile("[_.-]");


  protected void rememberName(String aliasName) {
    this.registeredName = aliasName;
  }

  @Override
  public String toString() {
    return this instanceof LibraryAlias ? "library " + getDependencyString() : "plugin " + getDependencyString();
  }
}
