package pocClientesCreditas.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pocClientesCreditas.service.OkhttpClientService


@RestController
@RequestMapping("okhttp")
class OkhttpClientController(
    private val okhttpClientService: OkhttpClientService = OkhttpClientService()
) {
    @GetMapping("sync")
    fun getAll() = okhttpClientService.reuestAll()

    @GetMapping("sync/{id}")
    fun byIdSincrono(@PathVariable id: String) = okhttpClientService.requestByIdSync(id)

    @GetMapping("async/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun byIdAsync(@PathVariable id: String) = okhttpClientService.getByIdAsync(id)
}



