package me.foreverigor.gradle.catalogs.impl;

import me.foreverigor.gradle.catalogs.api.VersionCatalog;

public interface AliasRegistration {

  void registerAlias(String aliasName, VersionCatalog catalog);

}
