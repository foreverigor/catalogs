package me.foreverigor.gradle.catalogs.api;

import org.gradle.api.Named;

public interface LibraryAlias extends DependencyAlias, Named {

  @Override
  default String getName() {
    return null;
  }

} // interface LibraryAlias
