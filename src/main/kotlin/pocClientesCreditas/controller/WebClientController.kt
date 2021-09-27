package pocClientesCreditas.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import pocClientesCreditas.service.webClientService


@RestController
@RequestMapping("web_client")
class WebClientController(
   val webClientService: webClientService = webClientService()
) {

    @GetMapping("/")
    fun getAll() = webClientService.requestAll()

    @GetMapping("sync/{id}")
    fun getSincrono(@PathVariable id: String) = webClientService.requestByIdSincrono(id)


    @GetMapping("async/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun getAssincrono(@PathVariable id: String) = webClientService.requestByIdAscincrono(id)
}