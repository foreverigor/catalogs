package me.foreverigor.gradle.catalogs.impl;

import me.foreverigor.gradle.catalogs.api.VersionCatalog;
import me.foreverigor.gradle.catalogs.api.VersionCatalogContainer;
import me.foreverigor.gradle.catalogs.util.JavaKotlinInterop;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class VersionCatalogContainerConfiguration implements VersionCatalogContainer {
    private final Function<String, Consumer<Consumer<VersionCatalogBuilder>>> catalogs;

    public VersionCatalogContainerConfiguration(Function<String, Consumer<Consumer<VersionCatalogBuilder>>> catalogs) {
        this.catalogs = catalogs;
    }

    @Override
    public void catalog(@NotNull String catalogPrefix) {}

    @Override
    public void catalog(@NotNull String catalogPrefix, @NotNull Function1<? super VersionCatalog, Unit> catalogConsumer) {
        String[] packages = splitPrefix(catalogPrefix);
        String topLevelCatalogName = packages[0];
        String aliasPrefix = packages.length > 1 ? catalogPrefix.substring(topLevelCatalogName.length() + 1) : "";

        catalogs.apply(topLevelCatalogName).accept(catalog -> {
            catalogConsumer.invoke(new PrefixVersionCatalog(catalog, aliasPrefix));
        });
    }

    @Override
    public void catalog(@NotNull String catalogName, @NotNull Consumer<VersionCatalog> catalogConsumer) {
        catalog(catalogName, JavaKotlinInterop.toKotlinFunction(catalogConsumer));
    }

    @NotNull
    private static String[] splitPrefix(String catalogPrefix) {
        String[] packages = catalogPrefix.split("\\.");
        if (packages.length == 0) throw new IllegalArgumentException("name");
        return packages;
    }
}
