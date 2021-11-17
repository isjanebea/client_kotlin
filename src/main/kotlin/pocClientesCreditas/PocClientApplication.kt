package pocClientesCreditas

import okhttp3.OkHttpClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.http.HttpHeaders.*

val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21lIjoiYmlhIiwiX2lkIjoiMSIsImlhdCI6MTYzMjc1MjA5MywiZXhwIjoxNjMzMzU2ODkzfQ.-E3r47xeJxLXUdUoj-NJAKQPSS20p8C3VSGVUQqoub8"
const val baseUrl = "http://localhost:3000"


@SpringBootApplication
@EnableFeignClients
class PocClientApplication(
) {
	@Bean
	fun webClient( builder: WebClient.Builder): WebClient {

		return builder
			.baseUrl(baseUrl)
			.defaultHeader(AUTHORIZATION, "Baerer $token")
			.defaultHeader(USER_AGENT,  "WebClient")
			.build()
	}

	@Bean
	fun okHttpClient () = OkHttpClient()
}

fun main(args: Array<String>) {
	runApplication<PocClientApplication>(*args)
}
