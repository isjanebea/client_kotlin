package pocClientesCreditas.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pocClientesCreditas.controller.request.PutRequest
import pocClientesCreditas.model.UserModelWebClient
import pocClientesCreditas.service.WebClientService
import java.io.File


@RestController
@RequestMapping("web_client")
class WebClientController(
   val webClientService: WebClientService
) {



    @GetMapping("sync/{id}")
    fun getSincrono(@PathVariable id: String) = webClientService.getById(id)

    @PostMapping("sync/create")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun create(@RequestBody body: UserModelWebClient) = webClientService.create(body)

    @PostMapping("sync/file")
    fun sendFile(@RequestParam file: MultipartFile) = webClientService.sendFile(file)


    @DeleteMapping("sync/delete")
    fun deleteUser(@RequestParam id: String) = webClientService.removeUser(id)

    @PutMapping("sync/update")
    fun updateUser(@RequestParam id: String, body: PutRequest) = webClientService.updateUser(id, body)
}