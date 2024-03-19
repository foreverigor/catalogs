package me.foreverigor.gradle.catalogs.api;

import me.foreverigor.gradle.CatalogsPlugin;
import me.foreverigor.gradle.catalogs.impl.VersionCatalogContainerConfiguration;
import org.gradle.api.Action;
import org.gradle.api.Named;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface VersionCatalogsConfiguration extends Consumer<VersionCatalogContainer>, Named {

    void configure(@NotNull VersionCatalogContainer catalogContainer);

    @Override
    default void accept(VersionCatalogContainer catalogContainer) {
        if (!getName().isEmpty()) CatalogsPlugin.Companion.getLogger().info("registering catalogs \"{}\"", getName());
        configure(catalogContainer);
    }

    @NotNull
    @Override
    default String getName() {
        return "";
    }

    default void configure(BiConsumer<String, Action<VersionCatalogBuilder>> catalogBuilderCreator) {
        Map<String, List<Consumer<VersionCatalogBuilder>>> catalogMap = new HashMap<>();

        this.accept(new VersionCatalogContainerConfiguration(name -> consumer -> catalogMap.computeIfAbsent(name, n -> new ArrayList<>()).add(consumer)));
        catalogMap.forEach((topLevelCatalogName, configs) -> {
            catalogBuilderCreator.accept(topLevelCatalogName, catalogBuilder -> configs.forEach(nestedCatalog -> nestedCatalog.accept(catalogBuilder)));
        });
    }
} // interface VersionCatalogsConfiguration
