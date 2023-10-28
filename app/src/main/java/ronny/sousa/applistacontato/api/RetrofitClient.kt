package ronny.sousa.applistacontato.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val BASE_URL = "https://web-4efiu6esn9ua.up-de-fra1-1.apps.run-on-seenode.com/"  // Substitua pela URL base

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val contatoService: ServicoContatos = 	retrofit.create(ServicoContatos::class.java)

}