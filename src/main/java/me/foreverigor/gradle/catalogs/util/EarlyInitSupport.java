package me.foreverigor.gradle.catalogs.util;

import me.foreverigor.gradle.catalogs.Catalogs;

public class EarlyInitSupport {

  static {
    maybeEarlyInit();
  }

  protected static void maybeEarlyInit() {
    var catalogs = Catalogs.INSTANCE;
    if (catalogs.isUnititialized()) {
      catalogs.init(null);
    }
  }

}
