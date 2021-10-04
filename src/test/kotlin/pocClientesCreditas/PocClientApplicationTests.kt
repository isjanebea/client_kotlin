package pocClientesCreditas

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import pocClientesCreditas.controller.request.WebClientRequestUser
import pocClientesCreditas.model.UserModelWebClient
import pocClientesCreditas.service.webClientService
import reactor.core.publisher.Mono
import reactor.test.StepVerifier



class PocClientApplicationTests {

	private  var mockServer: MockWebServer? = null
	private  var webClientService: webClientService? = null
	private  var objectMapper: ObjectMapper? = null
	private var baseURL: String? = null


	@BeforeEach
	fun inicialize() {
		mockServer = MockWebServer()
		mockServer!!.start(3000)

		baseURL =  String.format("http://localhost:%s",
			mockServer!!.port)

		webClientService = webClientService()
		objectMapper = ObjectMapper()
	}

	@AfterEach
	fun tearDown() {
		mockServer!!.shutdown()
	}

	@Test
	fun `deve retornar um usuario`() {
		var user = WebClientRequestUser("1", "Test")
		mockServer!!.enqueue(
			MockResponse().setBody(
				objectMapper!!.writeValueAsString(user)
			).addHeader(
				"Content-Type", MediaType.APPLICATION_JSON
			)
		)

		var response: Mono<WebClientRequestUser> = webClientService!!.requestByIdTest("${this.baseURL}/1")

		StepVerifier.create(response).expectNextMatches{ userResponse ->
			userResponse.name.equals(user.name)
		}.verifyComplete()

		//assertEquals(response!!.name, user.name)

	}

}
