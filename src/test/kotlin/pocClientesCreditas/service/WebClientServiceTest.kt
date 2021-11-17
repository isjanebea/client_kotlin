package pocClientesCreditas.service

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import pocClientesCreditas.baseUrl
import pocClientesCreditas.controller.request.PutRequest
import pocClientesCreditas.controller.request.WebClientRequestUser
import pocClientesCreditas.model.UserModelWebClient



class WebClientServiceTest {
    private var objectMapper: ObjectMapper = ObjectMapper()

    private lateinit var mockServer: MockWebServer
    private lateinit var webClientService: WebClientService
    private lateinit var webClient: WebClient

    @BeforeEach
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start(3000)

        webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build()
        webClientService = WebClientService(webClient)
    }

    @AfterEach
    fun tearDown() {
        mockServer.shutdown()
    }


    @Test
    fun `should return user by id`() {
        val userMock = WebClientRequestUser("1", "Test")

        mockServer.enqueue(
            MockResponse()
                .setBody(objectMapper.writeValueAsString(userMock))
                .addHeader("Content-Type", MediaType.APPLICATION_JSON)
        )

        val userResponse = webClientService.getById("1")
        assertEquals(userMock, userResponse)
    }

    @Test
    fun `should return an error when setting an id that doesn't exist`() {
        val statusCode = 400
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(statusCode)
                .setHttp2ErrorCode(statusCode)
        )

        assertThrows(CustomException::class.java) {
            webClientService.getById("1")
        }
    }


    @Test
    fun `Must make a post and receive and body`() {
        val userMock = UserModelWebClient("1", "test")

        mockServer.enqueue(
            MockResponse()
                .setBody(objectMapper.writeValueAsString(userMock))
        )

        webClientService.create(userMock)
        val takeRequest = mockServer.takeRequest()

        assertEquals("POST", takeRequest.method)
        assertNotNull(takeRequest.body)
    }

    @Test
    fun `should throw an error server`() {

        val userMock = UserModelWebClient("1", "Test")

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        assertThrows(CustomException::class.java) {
            webClientService.create(userMock)
        }

    }

    @Test
    fun `should return message success and method put`() {
        val userMock = PutRequest("test")
        val resMock = "{ \\\"message\\\" : \\\"success\\\"}"

        mockServer.enqueue(
            MockResponse()
                .setBody(resMock)
        )

        val response = webClientService.updateUser("1", userMock)
        val takeRequest = mockServer.takeRequest()


        assertEquals(resMock, response)
        assertEquals("/1", takeRequest.path)
        assertNotNull(takeRequest.body)
        assertEquals("PUT", takeRequest.method)
    }

}