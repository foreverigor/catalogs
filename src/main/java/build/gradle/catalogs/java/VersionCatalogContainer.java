package build.gradle.catalogs.java;

import build.gradle.catalogs.api.VersionCatalog;

import java.util.function.Consumer;

/**
 * gradle api equivalent is {@link org.gradle.api.initialization.resolve.MutableVersionCatalogContainer}
 */
public interface VersionCatalogContainer {

    void catalog(String catalogPrefix);
    void catalog(String catalogName, Consumer<VersionCatalog> catalogConsumer);

} // interface VersionCatalogBuilder
