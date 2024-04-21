@file:Suppress(
        "unused", // not true, used reflectively in Catalogs
        "PropertyName" // "Property name '*' should start with a lowercase letter" - nope
)
package me.foreverigor.gradle.catalogs

open class DefaultVersions(
        // By making them open constructor properties we get the cake and eat it too: can override in subclass
        // or set in constructor
        open var ApacheCommons: String = latestRelease,
        open var ApacheCommonsIo: String = latestRelease,
        open var ApacheHttp: String = latestRelease,
        open var ApacheHttp5: String = latestRelease,
        open var CommonsLogging: String = withoutVersion,

        open var Log4j: String = latestRelease,
        open var Slf4j: String = "1.7.36",
        open var Logback: String = latestRelease,

        open var Kotlin: String = latestRelease,
        open var KotlinX: String = latestRelease, // Doesn't follow Kotlin versioning
        open var Guava: String = latestRelease,
        open var Gson: String = latestRelease,
        open var Jackson: String = latestRelease,
        open var Reflections: String = withoutVersion,
        open var Objenesis: String = withoutVersion,

        open var Jetty: String = withoutVersion,
        open var Netty: String = withoutVersion,

        open var JBAnnotations: String = latestRelease,

        open var Junit: String = withoutVersion,
        open var JunitJupiter: String = withoutVersion,
        open var JunitBom: String = latestRelease,
        open var AssertJ: String = latestRelease,

        open var JakartaXml: String = withoutVersion,
        open var JakartaAnnotation: String = withoutVersion,
        open var JakartaServlet: String = withoutVersion,
        open var JakartaInject: String = withoutVersion,
        open var JakartaValidation: String = withoutVersion,
        open var JakartaJaxRs: String = withoutVersion,
        open var JakartaPersistence: String = withoutVersion,

        open var JakartaJsonP: String = withoutVersion,
        open var JakartaJsonB: String = withoutVersion,
        open var JakartaPlatform: String = withoutVersion,


        open var Spring: String = latestRelease,
        open var SpringBoot: String = withoutVersion,
        /**
         * Important because plugin version is mandatory but we don't want to set the regular dependencies' version
         * (which can be without version/ set by the plugin). if you want this behaviour you can set
         * ```
         * override var SpringBoot = SpringBootPlugin
         * ```
         * in your versions.
         */
        open var SpringBootPlugin: String = latestRelease,
        open var SpringDependencyManagement: String = latestRelease,

        open var Quarkus: String = withoutVersion,
        /**
         * I don't know if it makes sense to use the dependency versions without the platform
         */
        open var QuarkusPlatform: String = latestRelease,
        open var QuarkusPlugin: String = latestRelease,
        open var Spark: String = latestRelease,
        open var HelidonPlatform: String = latestRelease,
        open var Helidon: String = withoutVersion,

        open var GraalVM: String = latestRelease,

        open var ReactorTest: String = withoutVersion,
        open var RestAssured: String = withoutVersion,
        open var RxJava: String = latestRelease,

        open var Vavr: String = latestRelease,
        open var NoException: String = latestRelease,
        open var orgJson: String = latestRelease,
        open var Jsoup: String = latestRelease,

        open var Asm: String = latestRelease,
        open var Cglib: String = latestRelease,
        open var ByteBuddy: String = latestRelease,
        open var Javassist: String = latestRelease,

        open var ToolchainsPlugin: String = latestRelease,
        open var KordampPlugins: String = latestRelease
) {
    companion object {
        /**
         * [Gradle – Declaring Versions and Ranges](https://docs.gradle.org/current/userguide/single_versions.html)
         */
        const val latestRelease = "latest.release"
        const val withoutVersion = ""
    }
} // class DefaultVersions
