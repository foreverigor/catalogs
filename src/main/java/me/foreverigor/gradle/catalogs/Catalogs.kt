package me.foreverigor.gradle.catalogs

import me.foreverigor.gradle.catalogs.Catalogs.Plugins.gradleToolchains
import me.foreverigor.gradle.catalogs.Catalogs.Plugins.kordampSettings

import me.foreverigor.gradle.catalogs.DefaultVersions
import me.foreverigor.gradle.catalogs.api.VersionCatalogsConfiguration
import me.foreverigor.gradle.catalogs.util.CatalogsSupport
import me.foreverigor.gradle.catalogs.util.EarlyInitSupport
import me.foreverigor.gradle.catalogs.DefaultVersions as Versions

@Suppress("MemberVisibilityCanBePrivate") // No
object Catalogs : CatalogsSupport() {

    val aliases = catalogs("aliases") {
        catalog("spring") {
            // will be called spring.plugins.springBoot
            plugin("springBoot", springBootPlugin)
            plugin("dependencyManagement", springDepManagement)

            catalog("boot") {
                library("web", springBootWeb)
                library("webflux", springBootWebflux)
                library("test", springBootTest)
                library("actuator", springBootActuator)
            }
        } // catalog("spring")

        catalog("kotlin") { // FIXME hides the kotlin extension, rethink
            plugin("jvm", kotlinJvmPlugin)
            library("stdlib", kotlinStdlib)
            library("reflect", kotlinReflect)
        }

        catalog("junit") {
            library("jupiter", junitJupiter)
            library("bom", junit5Bom)
        }
    } // aliases

    val categories = catalogs("categories") {
        catalog("deps.logging") {
            catalog("slf4j") {
                bundle("slf4jSimple", slf4jApi, slf4jImplSimple)
                bundle("slf4jLogback", slf4jApi, logbackClassic)
                bundle("slf4jLog4j", slf4jApi, slf4jLog4jImpl)
            }

            catalog("jcl") {
                bundle("log4j", commonsLogging, log4jCore)
            }

            catalog("log4j") {
                bundle("log4j", log4jApi, log4jCore)
            }

            catalog("rainbowgum") { // New kid on the block
            }
        }
    } // categories

    val coordinates = catalogs("coordinates") {
        catalog("com") {
            catalog("google") {
                library("guava", "$prefix.guava", "guava", Versions::Guava)
                catalog("code") {
                    library("gson", "$prefix.gson", "gson", Versions::Gson)
                    library("findbugs.jsr305", "$prefix.findbugs", "jsr305")
                }
            }
            group("machinezoo.noexception") {
                module("noException", "noexception", Versions::NoException)
            }
            group("fasterxml.jackson.core") {
                module("databind", "jackson-databind", Versions::Jackson)
                module("core", "jackson-core", Versions::Jackson)
                module("annotations", "jackson-annotations", Versions::Jackson)
            }
            group("sparkjava") {
                module("sparkCore", "spark-core", Versions::Spark)
            }
        } // catalog("com")

        catalog("org") {
            catalog("slf4j").group {
                slf4jApi = module("api", "slf4j-api", Versions::Slf4j)
                slf4jImplSimple = module("simple", "slf4j-simple", Versions::Slf4j)
                module("jul", "jul-to-slf4j", Versions::Slf4j)
                module("log4j", "log4j-over-slf4j", Versions::Slf4j)
                module("jdk14", "slf4j-jdk14", Versions::Slf4j)
                module("nop", "slf4j-nop", Versions::Slf4j)
                module("reload4j", "slf4j-reload4j", Versions::Slf4j)
                module("jcl", "jcl-over-slf4j", Versions::Slf4j)

            }

            catalog("jetbrains") {
                library("annotations", prefix, "annotations", Versions::JBAnnotations)
            }

            catalog("jetbrains.kotlin").group {
                kotlinStdlib = module("stdlib", "kotlin-stdlib", Versions::Kotlin)
                module("stdlib7", "kotlin-stdlib7", Versions::Kotlin)
                module("stdlib8", "kotlin-stdlib8", Versions::Kotlin)
                kotlinReflect = module("reflect", "kotlin-reflect", Versions::Kotlin)
                module("test", "kotlin-test", Versions::Kotlin)
                module("testJunit", "kotlin-test-junit", Versions::Kotlin)
                module("gradlePlugin", "kotlin-gradle-plugin", Versions::Kotlin) // the kotlin plugin as library
                kotlinJvmPlugin = plugin("jvm", "$prefix.jvm", Versions::Kotlin)
            }

            catalog("jetbrains.kotlinx").group {
                module("coroutinesCore", "kotlinx-coroutines-core", Versions::KotlinX)
                module("coroutinesCoreJvm", "kotlinx-coroutines-core-jvm", Versions::KotlinX)
                module("serializationJson", "kotlinx-serialization-json-jvm", Versions::KotlinX)
            }

            catalog("springframework").group {
                module("springCore", "spring-core", Versions::Spring)
                module("springWeb", "spring-web", Versions::Spring)
                module("springBeans", "spring-beans", Versions::Spring)
            }

            catalog("springframework.boot").group {
                springBootPlugin = plugin("gradlePlugin", id = prefix, Versions::SpringBootPlugin)

                springBootWeb = module("web","spring-boot-starter-web", Versions::SpringBoot)
                springBootWebflux = module("webflux", "spring-boot-starter-webflux", Versions::SpringBoot)
                springBootTest = module("test", "spring-boot-starter-test", Versions::SpringBoot)
                springBootIntegration = module("integration", "spring-boot-starter-integration", Versions::SpringBoot)
                springBootActuator = module("actuator", "spring-boot-starter-actuator", Versions::SpringBoot)
                module("autoconfigure", "spring-boot-autoconfigure", Versions::SpringBoot)

                springBootJdbc = module("jdbc", "spring-boot-starter-jdbc", Versions::SpringBoot)
                springBootJersey = module("jersey", "spring-boot-starter-jersey", Versions::SpringBoot)
                springBootJooq = module("jooq", "spring-boot-starter-jooq", Versions::SpringBoot)

                module("devTools", "spring-boot-devtools", Versions::SpringBoot)

                catalog("data").group {
                    module("jdbc", "spring-boot-starter-data-jdbc", Versions::SpringBoot)
                    module("jpa", "spring-boot-starter-data-jpa", Versions::SpringBoot)
                    module("rest", "spring-boot-starter-data-rest", Versions::SpringBoot)
                }
            }
        } // catalog("org")
        catalog("org.apache") {
            group("commons") {
                library("lang3", prefix, "commons-lang3", Versions::ApacheCommons)
                library("collections4", prefix, "commons-collections4", Versions::ApacheCommons)
            }
            library("commons.io", "$prefix.commons-io", "commons-io", Versions::ApacheCommonsIo)

            group("logging.log4j") {
                log4jCore = module("core", "log4j-core", Versions::Log4j)
                log4jApi = module("api", "log4j-api", Versions::Log4j)
                slf4jLog4jImpl = module("slf4jImpl", "log4j-slf4j-impl", Versions::Log4j)
                module("slf4j2Impl", "log4j-slf4j2-impl", Versions::Log4j)
            }
            group("httpcomponents") {
                module("httpClient", "httpclient", Versions::ApacheHttp)
                module("httpCore", "httpcore", Versions::ApacheHttp)
                module("httpMime", "httpmime", Versions::ApacheHttp)

                group("client5") {
                    module("httpClient5", "httpclient5", Versions::ApacheHttp5)
                    module("httpClient5Fluent", "httpclient5-fluent", Versions::ApacheHttp5)
                }
                group("core5") {
                    module("httpCore5", "httpcore5", Versions::ApacheHttp5)
                }
            }
        } // catalog("org.apache")
        catalog("commons") {
            catalog("logging") {
                commonsLogging = library("commonsLogging", "commons-logging", "commons-logging", Versions::ApacheCommons)
            }
        }
        catalog("log4j") { // Log4j 1
            library("log4j", "log4j", "log4j")
        }
        catalog("org.gradle") {
            gradleToolchains = plugin("toolchains", "org.gradle.toolchains.foojay-resolver-convention", Versions::ToolchainsPlugin)
        }
        catalog("org.graalvm") {
            group("sdk") {
                module("graalSdk", "graal-sdk", Versions::GraalVM)
                module("nativeImage", "nativeimage", Versions::GraalVM)
            }
            group("polyglot") {
                module("polyglot", "polyglot", Versions::GraalVM)
                module("js", "js", Versions::GraalVM)
                module("jsCommunity", "js-community", Versions::GraalVM)
                module("python", "python", Versions::GraalVM)
                module("pythonCommunity", "python-community", Versions::GraalVM)
                module("ruby", "ruby", Versions::GraalVM)
                module("rubyCommunity", "ruby-community", Versions::GraalVM)
                module("llvmNative", "llvm-native", Versions::GraalVM)
                module("llvmNativeCommunity", "llvm-native-community", Versions::GraalVM)
            }
            group("python") {
                module("pythonLanguage", "python-language", Versions::GraalVM)
                module("pythonLauncher", "'python-launcher", Versions::GraalVM)
            }
        } // catalog("org.graalvm")
        catalog("org.eclipse") {
            group("jetty") {
                module("server", "jetty-server", Versions::Jetty)
                module("servlet", "jetty-servlet", Versions::Jetty)
                module("client", "jetty-client", Versions::Jetty)
            }
        }
        catalog("org.reflections") {
            library("reflections", prefix, "reflections", Versions::Reflections)
        }
        catalog("org.objenesis") {
            library("objenesis", prefix, "objenesis", Versions::Objenesis)
        }
        /*catalog("junit") {
            library("junit", prefix, "junit", Versions::Junit)
        }*/
        catalog("org.junit") {
            junitJupiter = library("jupiter", "$prefix.jupiter", "junit-jupiter", Versions::JunitJupiter)
            library("jupiter", "$prefix.jupiter", "junit-jupiter-api", Versions::JunitJupiter)
            library("jupiterApi", "$prefix.jupiter", "junit-jupiter-engine", Versions::JunitJupiter)
            junit5Bom = library("junit5", prefix, "junit-bom", Versions::JunitBom)
        }
        catalog("org.assertj") {
            library("assertjCore", prefix, "assertj-core", Versions::AssertJ)
        }
        catalog("org.json") {
            library("json", prefix, "json", Versions::Json)
        }
        catalog("org.jsoup") {
            library("jsoup", prefix, "jsoup", Versions::Jsoup)
        }
        catalog("org.kordamp") {
            group("gradle") {
                plugin("bom", "$prefix.bom", Versions::KordampPlugins)
                plugin("buildInfo", "$prefix.build-info", Versions::KordampPlugins)
                plugin("checkstyle", "$prefix.checkstyle", Versions::KordampPlugins)
                plugin("codenarc", "$prefix.codenarc", Versions::KordampPlugins)
                plugin("coveralls", "$prefix.coveralls", Versions::KordampPlugins)
                plugin("errorprone", "$prefix.errorprone", Versions::KordampPlugins)
                plugin("functionalTest", "$prefix.functional-test", Versions::KordampPlugins)
                plugin("groovydoc", "$prefix.groovydoc", Versions::KordampPlugins)
                plugin("guide", "$prefix.guide", Versions::KordampPlugins)
                plugin("integrationTest", "$prefix.integration-test", Versions::KordampPlugins)
                plugin("jacoco", "$prefix.jacoco", Versions::KordampPlugins)
                plugin("jar", "$prefix.jar", Versions::KordampPlugins)
                plugin("javadoc", "$prefix.javadoc", Versions::KordampPlugins)
                plugin("licensing", "$prefix.licensing", Versions::KordampPlugins)
                plugin("minpom", "$prefix.minpom", Versions::KordampPlugins)
                plugin("plugin", "$prefix.plugin", Versions::KordampPlugins)
                plugin("pmd", "$prefix.pmd", Versions::KordampPlugins)
                plugin("project", "$prefix.project", Versions::KordampPlugins)
                plugin("publishing", "$prefix.publishing", Versions::KordampPlugins)
                plugin("sonar", "$prefix.sonar", Versions::KordampPlugins)
                plugin("sourceJar", "$prefix.source-jar", Versions::KordampPlugins)
                plugin("sourceStats", "$prefix.source-stats", Versions::KordampPlugins)
                plugin("spotbugs", "$prefix.spotbugs", Versions::KordampPlugins)
                plugin("testing", "$prefix.testing", Versions::KordampPlugins)
                kordampSettings = plugin("settings", "$prefix.settings", Versions::KordampPlugins)

            }
        }
        catalog("org.javassist") {
            library("javassist", prefix, "javassist", Versions::Javassist)
        }
        catalog("org.ow2.asm") {
            library("asm", prefix, "asm", Versions::Asm)
        }
        catalog("cglib") {
            library("nodep", prefix, "cglib-nodep", Versions::Cglib)
            library("cglib", prefix, "cglib", Versions::Cglib)
        }
        catalog("net.bytebuddy") {
            library("byteBuddy", prefix, "byte-buddy", Versions::ByteBuddy)
            library("byteBuddyAgent", prefix, "byte-buddy-agent", Versions::ByteBuddy)
            library("byteBuddyDep", prefix, "byte-buddy-dep", Versions::ByteBuddy)
        }

        catalog("io.vavr") {
            library("vavr", prefix, "vavr", Versions::Vavr)
        }

        catalog("io") {
            group("quarkus") {
                plugin("gradlePlugin", prefix, Versions::QuarkusPlugin)

                library("platform", "$prefix.platform", "quarkus-bom", Versions::QuarkusPlatform)

                module("core", "quarkus-core", Versions::Quarkus)
                module("arc", "quarkus-arc", Versions::Quarkus)
                module("resteasyReactive", "quarkus-resteasy-reactive", Versions::Quarkus)
                module("junit5", "quarkus-junit5", Versions::Quarkus)
            }

            group("helidon") {
                module("platform", "helidon-bom", Versions::HelidonPlatform)
                // TODO add more helidon
            }

            catalog("spring") {
                springDepManagement = plugin("dependencyManagement", "io.spring.dependency-management", Versions::SpringDependencyManagement)
            }
            group("netty") {
                module("all", "netty-all", Versions::Netty)
            }
            catalog("projectreactor") {
                library("test", prefix, "reactor-test", Versions::ReactorTest)
            }
            catalog("restassured") { // rest-assured would've been split apart
                library("restAssured", "io.rest-assured", "rest-assured", Versions::RestAssured)
            }
            catalog("reactivex") {
                library("rxjava3", "io.reactivex.rxjava3", "rxjava")
            }
        } // catalog("io")

        catalog("ch.qos") {
            group("logback") {
                logbackClassic = module("logbackClassic", "logback-classic", Versions::Logback)
                module("logbackCore", "logback-core", Versions::Logback)
            }
        }

        catalog("jakarta") {
            library("injectApi", "jakarta.inject", "jakarta.inject-api", Versions::JakartaInject)
            library("validationApi", "jakarta.validation", "jakarta.validation-api", Versions::JakartaValidation)
        }

        catalog("javax") {
            library("inject", "javax.inject", "javax.inject", DefaultVersions.latestRelease)
        }
    } // coordinates

    // Shared between catalogs (links):
    var slf4jApi by libs
    var slf4jImplSimple by libs
    var slf4jLog4jImpl by libs
    var logbackClassic by libs
    var commonsLogging by libs
    var log4jApi by libs
    var log4jCore by libs

    private var kotlinJvmPlugin by plugins
    private var kotlinStdlib by libs
    private var kotlinReflect by libs

    private var springBootPlugin by plugins
    private var springDepManagement by plugins

    private var springBootWeb by libs
    private var springBootWebflux by libs
    private var springBootIntegration by libs
    private var springBootJdbc by libs
    private var springBootJersey by libs
    private var springBootJooq by libs
    private var springBootTest by libs
    private var springBootActuator by libs

    private var junitJupiter by libs
    private var junit5Bom by libs

    object Plugins : EarlyInitSupport() {
        var gradleToolchains by plugins
        var kordampSettings by plugins
    }

    override val catalogsConfigs: List<VersionCatalogsConfiguration> = listOf(coordinates, aliases, categories)

} // object Catalogs

