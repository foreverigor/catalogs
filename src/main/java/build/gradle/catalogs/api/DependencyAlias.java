package build.gradle.catalogs.api;

import build.gradle.catalogs.DefaultVersions;
import build.gradle.catalogs.link.DefaultLibraryAlias;
import build.gradle.catalogs.link.VersionRefLibraryAlias;
import build.gradle.catalogs.link.VersionRefPluginAlias;
import kotlin.reflect.KProperty1;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;

/**
 * an abstract alias that can be registered with {@link VersionCatalogBuilder}
 */
public interface DependencyAlias {

  static LibraryAlias create(String groupArtifactVersion) {
    return DefaultLibraryAlias.create(groupArtifactVersion);
  }

  static PluginAlias create(String pluginId, KProperty1<DefaultVersions, String> versionRef) {
    return new VersionRefPluginAlias(pluginId, versionRef);
  }

  static LibraryAlias create(String group, String artifact, String version) {
    return DefaultLibraryAlias.create(group, artifact, version);
  }

  static LibraryAlias.NoVersion create(String group, String artifact) {
    return DefaultLibraryAlias.create(group, artifact);
  }

  static LibraryAlias create(String group, String artifact, KProperty1<DefaultVersions, String> versionRef) {
    return new VersionRefLibraryAlias(group, artifact, versionRef);
  }

  DependencyAlias register(String alias, VersionCatalog catalog);


}
