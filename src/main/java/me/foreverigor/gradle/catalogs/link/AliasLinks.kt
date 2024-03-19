package me.foreverigor.gradle.catalogs.link

import me.foreverigor.gradle.CatalogsPlugin
import org.gradle.api.IllegalDependencyNotation
import kotlin.reflect.KProperty

open class AliasLinks<T> {

    private val properties: MutableMap<String, T> = mutableMapOf()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return properties.getOrElse(property.name) { throw IllegalDependencyNotation("the alias link '" + property.name + "' wasn't assigned anything") }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (properties.containsKey(property.name)) {
            CatalogsPlugin.Logger.warn("attempted duplicate assignment of alias link '{}' was ignored", property.name)
        } else {
            properties[property.name] = value
        }
    }
}