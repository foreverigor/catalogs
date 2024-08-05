package me.foreverigor.gradle.catalogs.impl;

import kotlin.reflect.KProperty1;
import me.foreverigor.gradle.catalogs.CatalogVersions;

import java.util.function.Supplier;

public interface DepVersion extends Supplier<String> {

  static Literal fromLiteral(String version) {
    return () -> version;
  }

  static DepVersionReference fromRef(KProperty1<CatalogVersions, String> version) {
    return new DepVersionReference(version);
  }

  interface Literal extends DepVersion {}

} // interface DepVersion
