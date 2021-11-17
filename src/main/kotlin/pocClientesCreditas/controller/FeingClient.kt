package pocClientesCreditas.controller

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import pocClientesCreditas.baseUrl

@FeignClient(value = "feign", url = baseUrl)
interface UserClient {
    @get:RequestMapping(method = [RequestMethod.GET], value = ["/posts"])
    val users: List<Any>

    @RequestMapping(method = [RequestMethod.GET], value = ["/posts/{postId}"], produces = ["application/json"])
    fun getUserById(@PathVariable("postId") postId: Long?): Any
}


