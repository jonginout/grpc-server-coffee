package site.jongin.coffeegrpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoffeeGrpcApplication

fun main(args: Array<String>) {
    runApplication<CoffeeGrpcApplication>(*args)
}
