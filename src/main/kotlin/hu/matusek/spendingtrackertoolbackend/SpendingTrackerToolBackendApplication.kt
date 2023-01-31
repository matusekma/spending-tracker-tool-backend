package hu.matusek.spendingtrackertoolbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpendingTrackerToolBackendApplication

fun main(args: Array<String>) {
	runApplication<SpendingTrackerToolBackendApplication>(*args)
}
