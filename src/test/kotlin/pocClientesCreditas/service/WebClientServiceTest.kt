package pocClientesCreditas.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType
import pocClientesCreditas.controller.request.WebClientRequestUser
import pocClientesCreditas.controller.response.WebClientUserResponseById
import reactor.test.StepVerifier

@ExtendWith(MockKExtension::class)
class WebClientServiceTest {
    private var objectMapper: ObjectMapper = ObjectMapper()

    private lateinit var mockServer: MockWebServer

    @RelaxedMockK
    private lateinit var webClientService: WebClientService


    @BeforeEach
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start(3000)
    }

    @AfterEach
    fun tearDown() {
        mockServer.shutdown()
    }


    @Test
    fun `should return user by id`() {
        val userMock = WebClientRequestUser("1", "Test")

        mockServer.enqueue(
            MockResponse().setBody(
                objectMapper.writeValueAsString(userMock)
            ).addHeader(
                "Content-Type", MediaType.APPLICATION_JSON
            )
        )

        val userResponse = webClientService.requestByIdSincrono("1")
        assertEquals(userMock, userResponse)
    }

    @Test
    fun `should return an error when setting an id that doesn't exist`() {

    }

}
