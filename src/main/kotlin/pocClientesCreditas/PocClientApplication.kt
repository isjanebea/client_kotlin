package pocClientesCreditas

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient

val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21lIjoiYmlhIiwiX2lkIjoiMSIsImlhdCI6MTYzMjc1MjA5MywiZXhwIjoxNjMzMzU2ODkzfQ.-E3r47xeJxLXUdUoj-NJAKQPSS20p8C3VSGVUQqoub8"
val baseUrl = "http://localhost:3000"


@SpringBootApplication
class PocClientApplication(
	@Value("\${apits.users.url}")
	private val baseUrl: String
) {
	@Bean
	fun webClient( builder: WebClient.Builder): WebClient {

		return builder
			.baseUrl(this.baseUrl)
			.defaultHeader("authorization", "Baerer $token")
			.defaultHeader("User-Agent",  "webClient test")
			.build()
	}
}

fun main(args: Array<String>) {
	runApplication<PocClientApplication>(*args)
}
