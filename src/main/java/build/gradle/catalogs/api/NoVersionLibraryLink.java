package build.gradle.catalogs.api;

import org.jetbrains.annotations.NotNull;

public interface NoVersionLibraryLink extends LibraryAlias {

    @NotNull
    LibraryAlias version(@NotNull String version);

}
