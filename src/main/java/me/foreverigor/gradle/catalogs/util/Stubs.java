package me.foreverigor.gradle.catalogs.util;

import me.foreverigor.gradle.catalogs.impl.Stub;
import org.gradle.api.Action;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.gradle.api.provider.Property;

import java.util.List;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class Stubs {

  public static void createStubCatalog(String realCatalogName, Action<VersionCatalogBuilder> consumer) {
    consumer.execute(stubVersionCatalogBuilder(realCatalogName));
  }

  private static class StubAliasBuilder implements VersionCatalogBuilder.LibraryAliasBuilder, VersionCatalogBuilder.PluginAliasBuilder, Stub {
    @Override
    public void version(Action<? super MutableVersionConstraint> versionSpec) {

    }

    @Override
    public void version(String version) {

    }

    @Override
    public void versionRef(String versionRef) {

    }

    @Override
    public void withoutVersion() {

    }
  } // class StubAliasBuilder

  private static VersionCatalogBuilder stubVersionCatalogBuilder(String realName) {
    return new StubVersionCatalogBuilder(realName);
  }

  private static class StubVersionCatalogBuilder implements VersionCatalogBuilder, Stub {
    private final String realName;

    public StubVersionCatalogBuilder(String realName) {
      this.realName = realName;
    }

    @Override
    public Property<String> getDescription() {
      return null;
    }

    @Override
    public void from(Object dependencyNotation) {

    }

    @Override
    public String version(String alias, Action<? super MutableVersionConstraint> versionSpec) {
      return null;
    }

    @Override
    public String version(String alias, String version) {
      return null;
    }

   /* @Override
    public AliasBuilder alias(String alias) {
      return null;
    }*/

    @Override
    public LibraryAliasBuilder library(String alias, String group, String artifact) {
      return new StubAliasBuilder();
    }

    @Override
    public void library(String alias, String groupArtifactVersion) {

    }

    @Override
    public PluginAliasBuilder plugin(String alias, String id) {
      return new StubAliasBuilder();
    }

    @Override
    public void bundle(String alias, List<String> aliases) {
    }

    @Override
    public String getLibrariesExtensionName() {
      return null;
    }

    @Override
    public String getName() {
      return realName;
    }
  }
} // interface Stubs
