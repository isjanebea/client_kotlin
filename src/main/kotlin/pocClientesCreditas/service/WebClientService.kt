package pocClientesCreditas.service

import org.apache.tomcat.util.codec.binary.Base64
import org.apache.tomcat.util.codec.binary.StringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import pocClientesCreditas.controller.request.PutRequest
import pocClientesCreditas.controller.request.WebClientRequestUser
import pocClientesCreditas.controller.request.WebClientSendPdf
import pocClientesCreditas.model.UserModelWebClient
import pocClientesCreditas.token
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.io.IOException

/*
Preciso injetar as dependencias, utilizando injecao de dependencia por parametros

*/

@Service
class WebClientService(
    val webClient: WebClient
) {

    fun getById(id: String) = webClient // aqui execulta a requisicao
        .get()
        .uri("/{id}", id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(HttpStatus::isError) { response ->
            if (response.statusCode().is4xxClientError) Mono.error(CustomException(is400Error))
            else Mono.error(CustomException(is500Error))
        }
        .bodyToMono(WebClientRequestUser::class.java)
        .block()


    fun create(user: UserModelWebClient) = webClient
        .post()
        .uri("/create")
        .body(Mono.just(user), UserModelWebClient::class.java)
        .header("authorization","Baerer $token")
        .retrieve()
        .onStatus(HttpStatus::isError) { response ->
            if (response.statusCode().is4xxClientError) Mono.error(CustomException(is400Error))
            else Mono.error(CustomException(is500Error))
        }
        .bodyToMono(String::class.java)
        .block()

    fun sendFile(file: MultipartFile) = webClient
        .post()
        .uri("/file")
        .body(Mono.just(WebClientSendPdf().toBase64(file)), WebClientSendPdf::class.java)
        .retrieve()
        .onStatus(HttpStatus::isError) { response ->
            if (response.statusCode().is4xxClientError) Mono.error(CustomException(is400Error))
            else Mono.error(CustomException(is500Error))
        }
        .bodyToMono(String::class.java)
        .block()


    fun removeUser(id: String) = webClient
        .delete()
        .uri("/{id}", id)
        .retrieve()
        .bodyToMono(String::class.java)
        .block()

    fun updateUser(id: String, user: PutRequest): String? {
        val user = WebClientRequestUser(id, user.name)
        val res = webClient
            .put()
            .uri("/{id}", id)
            .bodyValue(user)
            .retrieve()
            .toEntity(String::class.java)
            .block()

    }


}


val is500Error = "Error no servidor remoto"
val is400Error = "Error no servidor remoto"