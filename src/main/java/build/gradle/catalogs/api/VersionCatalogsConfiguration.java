package build.gradle.catalogs.api;

import build.gradle.catalogs.impl.VersionCatalogContainerConfiguration;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@FunctionalInterface
public interface VersionCatalogsConfiguration extends Consumer<VersionCatalogContainer> {

    void configure(@NotNull VersionCatalogContainer catalogContainer);

    @Override
    default void accept(VersionCatalogContainer catalogContainer) {
        configure(catalogContainer);
    }

    default void configure(MutableVersionCatalogContainer catalogs) {
        Map<String, List<Consumer<VersionCatalogBuilder>>> catalogMap = new HashMap<>();

        this.accept(new VersionCatalogContainerConfiguration(name -> consumer -> catalogMap.computeIfAbsent(name, n -> new ArrayList<>()).add(consumer)));
        catalogMap.forEach((catalogName, catalogConfigs) -> {
            catalogs.create(catalogName, catalog -> {
                catalogConfigs.forEach(config -> config.accept(catalog));
            });
        });
    }
} // interface VersionCatalogsConfiguration
