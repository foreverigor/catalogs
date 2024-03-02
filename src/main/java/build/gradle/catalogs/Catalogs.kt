package build.gradle.catalogs

import build.gradle.catalogs.api.*
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer
import java.util.function.Consumer

@Suppress("MemberVisibilityCanBePrivate")
object Catalogs {

    val aliases = catalogs {
        catalog("spring") {
            // will be called spring.plugins.springBoot
            plugin("springBoot", springBootPlugin)
            plugin("dependencyManagement", springDependencyManagement)

            catalog("boot") {
                library("web", springBootWeb)
                library("webflux", springBootWebflux)
                library("test", springBootTest)
                library("actuator", springBootActuator)
            }
        } // catalog("spring")

        catalog("junit") {

        }
    } // val aliases

    val coordinates = catalogs {
        catalog("org") {
            catalog("jetbrains") {
                catalog("kotlin") {
                    plugin("jvm", "$prefix.jvm", "1.9.22")
                }
            }
            catalog("springframework.boot") {
                springBootWeb = library("web", prefix, "spring-boot-starter-web", withoutVersion = true)
                springBootWebflux = library("webflux", prefix, "spring-boot-starter-webflux", withoutVersion = true)
                springBootTest = library("test", prefix, "spring-boot-starter-test", withoutVersion = true)
                springBootActuator = library("actuator", prefix, "spring-boot-starter-actuator", withoutVersion = true)
                springBootPlugin = plugin("gradlePlugin", prefix, "3.2.2")
            }
        }
        catalog("org.junit") {
            library("jupiter", "org.junit.jupiter", "junit-jupiter").withoutVersion() // FIXME
            library("bom", "org.junit:junit-bom:5.9.1")
        }
        catalog("io") {
            catalog("spring") {
                springDependencyManagement = plugin("dependencyManagement", "io.spring.dependency-management", "1.1.4")
            }
            catalog("projectreactor") {
                library("test", prefix, "reactor-test").withoutVersion()
            }
        }
    } // val coordinates

    // Shared:
    private val libraries: MutableMap<String, LibraryLink> = mutableMapOf()
    private val plugins: MutableMap<String, PluginLink> = mutableMapOf()

    var springBootWeb by libraries
    var springBootWebflux by libraries
    var springBootTest by libraries
    var springBootActuator by libraries
    var springBootPlugin by plugins
    var springDependencyManagement by plugins
    private fun catalogs(conf: VersionCatalogContainer.() -> Unit): VersionCatalogsConfiguration {
        return VersionCatalogsConfiguration(conf)
    }

    private val configs: Set<VersionCatalogsConfiguration> = setOf(coordinates, aliases)

    val catalogsConfig = Consumer { catalogs: MutableVersionCatalogContainer ->
        configs.forEach(Consumer { it.configure(catalogs) })
    }

} // object Catalogs

