package build.gradle.catalogs.api;

import org.jetbrains.annotations.NotNull;

public interface HybridLibraryLink extends LibraryLink {

    @NotNull
    LibraryLink version(@NotNull String version);

}
