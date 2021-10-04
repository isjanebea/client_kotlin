package pocClientesCreditas.service

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import pocClientesCreditas.model.UserModelWebClient
import org.junit.jupiter.api.Assertions.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class webClientServiceTest(
    private var objectMapper: ObjectMapper = ObjectMapper()
) {

    private lateinit var mockServer: MockWebServer
    private lateinit var webClientService: webClientService
    @BeforeEach
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start(8081)
    }

    @AfterEach
    fun tearDown() {
        mockServer.shutdown()
    }


    @Test
    fun `deve retornar um usuario`() {
          var User = UserModelWebClient("1", "Test")
            mockServer.enqueue(MockResponse().setBody(
                objectMapper.writeValueAsString(User)
            ).addHeader(
                "Content-Type", "applciation/json"
            ))

       var response = webClientService.requestByIdSincrono("1")

        assertEquals(User, response)
    }

}


