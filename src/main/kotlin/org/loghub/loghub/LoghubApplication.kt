package org.loghub.loghub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoghubApplication

fun main(args: Array<String>) {
    runApplication<LoghubApplication>(*args)
}
