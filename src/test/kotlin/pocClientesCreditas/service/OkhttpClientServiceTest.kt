package pocClientesCreditas.service

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import pocClientesCreditas.controller.request.OkHttpRequestUser
import pocClientesCreditas.controller.response.OkHttpClientUserResponseById

class OkhttpClientServiceTest {
    private lateinit var okhttpClientService: OkhttpClientService
    private lateinit var mockWebServer: MockWebServer
    private val objectMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun setup() {
         mockWebServer = MockWebServer()
         mockWebServer.start(3000)
         okhttpClientService = OkhttpClientService(OkHttpClient())
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
    @Test
    fun requestByIdSync() {
        val requestUserMock = OkHttpRequestUser("1", "Joe Doe")
        val responseUserMock = OkHttpClientUserResponseById(requestUserMock._id, requestUserMock.name)
        mockWebServer.enqueue(
            MockResponse()
                .setBody(objectMapper.writeValueAsString(requestUserMock))
        )

        val responseById = okhttpClientService.requestByIdSync("1")

        assertEquals(responseUserMock, responseById)
    }
    @Test
    fun create() {
        val requestUserMock = OkHttpRequestUser("1", "Joe Doe")
        val responseUserMock = OkHttpClientUserResponseById(requestUserMock._id, requestUserMock.name)
        mockWebServer.enqueue(
            MockResponse()
                .setBody(objectMapper.writeValueAsString(requestUserMock))
        )

         okhttpClientService.create(requestUserMock)

        val takeRequest = mockWebServer.takeRequest()

        assertEquals("POST", takeRequest.method)
        assertNotNull(takeRequest.body)
    }
}