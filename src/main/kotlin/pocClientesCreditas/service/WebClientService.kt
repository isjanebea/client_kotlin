package pocClientesCreditas.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.reactive.function.client.toEntityList
import pocClientesCreditas.baseUrl
import pocClientesCreditas.controller.request.WebClientRequestUser
import pocClientesCreditas.controller.response.OkHttpClientUserResponseById
import pocClientesCreditas.controller.response.WebClientUserResponseById
import pocClientesCreditas.model.UserModelWebClient
import reactor.core.publisher.Mono
import java.io.IOException

/*
Preciso injetar as dependencias, utilizando injecao de dependencia por parametros

*/

@Service
class WebClientService(
    val webClient: WebClient
) {


    // aqui seria sincrono
    fun requestByIdSincrono(id: String) = webClient // aqui execulta a requisicao
            .get()
            .uri("/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(WebClientRequestUser::class.java)
            .block()



    fun requestByIdAscincrono(id: String) {



        var entityMono: Mono<ResponseEntity<WebClientRequestUser>> = webClient // aqui execulta a requisicao
            .get()
            .uri("/$id")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(WebClientRequestUser::class.java)

         entityMono.subscribe { responseEntity ->
             println(responseEntity.body)
         }

    }

    fun requestAll(): List<OkHttpClientUserResponseById>? {

        var entityListMono: Mono<ResponseEntity<List<WebClientRequestUser>>> = webClient // aqui execulta a requisicao
            .get()
            .uri("/")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntityList(WebClientRequestUser::class.java)

            entityListMono.block()?.let { res ->
                if (res.statusCodeValue != 200) throw IOException(" ERRO ao Obter os dados")
                return res.body!!.map { user ->
                    OkHttpClientUserResponseById(user._id, user.name)
                }
            }
        return  null
    }


    // post de maneira sincrona

    // post de maneira asyncrona

}
