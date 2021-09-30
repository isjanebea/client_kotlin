package pocClientesCreditas.service

import org.assertj.core.api.Assertions


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import pocClientesCreditas.controller.WebClientController

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class webClientServiceTest {

    @Autowired
    private val context: ApplicationContext? = null
    @Autowired
    private val controller: WebClientController? = null

    var testClient = WebTestClient.bindToApplicationContext(context!!)
        .build()
    var webTestClient = WebTestClient.bindToController(controller).build()

    @Test
    fun `shold to expected status code is ok`() {
         webTestClient
            .get()
            .uri("/")
            .exchange()
            .expectStatus().isOk()
             .expectBody()
             .jsonPath("$.name").isEqualTo("Bea");
    }

}


