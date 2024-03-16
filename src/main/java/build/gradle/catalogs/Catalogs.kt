package build.gradle.catalogs

import build.gradle.catalogs.api.VersionCatalogsConfiguration
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer
import java.util.function.Consumer

@Suppress("MemberVisibilityCanBePrivate")
object Catalogs : CatalogsSupport() {

    val aliases = catalogs("aliases") {
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
            library("jupiter", junitJupiter)
        }
    } // aliases

    val coordinates = catalogs("coordinates") {
        catalog("org") {
            catalog("jetbrains") {
                catalog("kotlin") {
                    plugin("jvm", "$prefix.jvm", "1.9.22")
                }
            }
            catalog("springframework.boot") {
                springBootPlugin = plugin("gradlePlugin", prefix, "3.2.3")

                springBootWeb = library("web", prefix, "spring-boot-starter-web", withoutVersion)
                springBootWebflux = library("webflux", prefix, "spring-boot-starter-webflux", withoutVersion)
                springBootTest = library("test", prefix, "spring-boot-starter-test", withoutVersion)
                springBootActuator = library("actuator", prefix, "spring-boot-starter-actuator", withoutVersion)
            }
        }
        catalog("org.junit") {
            junitJupiter = library("jupiter", "org.junit.jupiter", "junit-jupiter", withoutVersion)
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
    } // coordinates

    // Shared between catalogs (links):
    private var springBootPlugin by plugins
    private var springDependencyManagement by plugins

    private var springBootWeb by libs
    private var springBootWebflux by libs
    private var springBootTest by libs
    private var springBootActuator by libs
    private var junitJupiter by libs

    val configs: List<VersionCatalogsConfiguration> = listOf(coordinates, aliases)

    val catalogsConfig = Consumer { catalogs: MutableVersionCatalogContainer ->
        configs.forEach(Consumer { it.configure(catalogs) })
    }

} // object Catalogs

