package build.gradle.catalogs.link

import build.gradle.CatalogsPlugin
import org.gradle.api.IllegalDependencyNotation
import kotlin.reflect.KProperty

open class DependencyLinks<T> {

    private val properties: MutableMap<String,T> = mutableMapOf()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return properties.getOrElse(property.name) { throw IllegalDependencyNotation("the alias link \"" + property.name + "\" wasn't defined") }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (properties.containsKey(property.name)) {
            CatalogsPlugin.Logger.warn("attempted duplicate assignment of alias link \"" + property.name + "\" was ignored")
        } else {
            properties[property.name] = value
        }
    }
}