package pocClientesCreditas.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pocClientesCreditas.baseUrl
import pocClientesCreditas.controller.request.OkHttpRequestUser
import pocClientesCreditas.controller.response.OkHttpClientUserResponseById
import java.io.IOException


@Service
class OkhttpClientService(
    private val client: OkHttpClient = OkHttpClient(),
    private val gson: Gson = Gson(),
) {

    fun reuestAll(): List<OkHttpRequestUser> {
        val request = Request.Builder()
            .url(baseUrl)
            .build()

        var result: List<OkHttpRequestUser>


        // chamada do client
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            // converter para uma instancia

            result = gson  // json parse
                .fromJson(
                    response.body()!!.string(),  // response body
                    object : TypeToken<List<OkHttpRequestUser>>() {}.type // typeOf
                )
        }


        return result
    }


    fun requestByIdSync(id: String): OkHttpClientUserResponseById {
        val request = Request.Builder()
            .url("$baseUrl/$id")
            .build()


        // chamada do client //
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            // converter para uma instancia

           var result: OkHttpRequestUser = gson  // json parse
                .fromJson(
                    response.body()!!.string(),  // response body
                    object : TypeToken<OkHttpRequestUser>() {}.type // typeOf
                )

            return OkHttpClientUserResponseById(
                id= result._id,
                name = result.name
            )
        }



    }

    fun getByIdAsync(id: String) {
        val request = Request.Builder()
            .url("$baseUrl/$id")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")


                    var result: OkHttpClientUserResponseById = gson  // json parse
                        .fromJson(
                            response.body()!!.string(),  // response body
                            object : TypeToken<OkHttpClientUserResponseById>() {}.type // typeOf
                        )
                    // nao tratei a resposta adquadamente
                    println(result)
                }
            }
        })
    }

    // post de maneira sincrona

    // post de maneira assincrona
}
