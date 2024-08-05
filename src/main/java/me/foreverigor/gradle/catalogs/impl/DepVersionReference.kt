package me.foreverigor.gradle.catalogs.impl

class DepVersionReference(val version: VersionRef) : DepVersion {
  override fun get() = version.refName
}
