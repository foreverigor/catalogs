package catalogsPlugin.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ExampleApplication

fun main(args: Array<String>) {
  runApplication<ExampleApplication>(*args)
}
