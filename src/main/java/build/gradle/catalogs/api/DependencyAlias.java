package build.gradle.catalogs.api;

import org.gradle.api.initialization.dsl.VersionCatalogBuilder;

/**
 * an abstract alias that can be registered with {@link VersionCatalogBuilder}
 */
public interface DependencyAlias {

  void register(String alias, VersionCatalogBuilder catalog);

}
