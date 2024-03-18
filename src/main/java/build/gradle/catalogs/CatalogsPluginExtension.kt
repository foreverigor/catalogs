package build.gradle.catalogs

import build.gradle.catalogs.impl.CatalogVersions

open class CatalogsPluginExtension {

    companion object {
        val TYPE = CatalogsPluginExtension::class.java
    }

    var versions: DefaultVersions = CatalogVersions

}
