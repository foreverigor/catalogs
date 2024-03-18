package me.foreverigor.gradle.catalogs

import me.foreverigor.gradle.catalogs.impl.CatalogVersions
import org.gradle.api.initialization.Settings

fun Settings.gradleCatalogue(configure: CatalogsPluginExtension.() -> Unit) {
    extensions.getByType(CatalogsPluginExtension.TYPE).configure()
}

open class CatalogsPluginExtension {

    companion object {
        val NAME = Settings::gradleCatalogue.name
        val TYPE = CatalogsPluginExtension::class.java
    }

    var versions: DefaultVersions = CatalogVersions

}