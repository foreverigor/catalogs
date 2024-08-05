package me.foreverigor.gradle.catalogs.util;

import org.gradle.api.Action;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;

import me.foreverigor.gradle.CatalogsPlugin;

public class Stubs {

  public static void createStubCatalog(String realCatalogName, Action<VersionCatalogBuilder> consumer) {
    CatalogsPlugin.Companion.getLogger().warn("Stub init not yet supported");
  }

} // interface Stubs
