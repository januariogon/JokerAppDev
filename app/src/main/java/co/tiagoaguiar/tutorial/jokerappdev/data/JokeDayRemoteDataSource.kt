package co.tiagoaguiar.tutorial.jokerappdev.data

import co.tiagoaguiar.tutorial.jokerappdev.model.Joke
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JokeDayRemoteDataSource {
    fun findRandom(callback: JokeCallback){
        HTTPClient.retrofit()
            .create(ChuckNorrisAPI::class.java)
            .findRandom()
            .enqueue(object : Callback<Joke> {
                override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                    if (response.isSuccessful){
                        val joke = response.body()
                        callback.onSuccess(joke ?: throw RuntimeException("Piada Não Encontrada"))
                    }else{
                        val error = response.errorBody()?.toString()
                        callback.onError(error ?: "Erro Desconhecido")
                    }

                    callback.onComplete()
                }

                override fun onFailure(call: Call<Joke>, t: Throwable) {
                    callback.onError(t.message ?: "Erro Interno")
                    callback.onComplete()
                }

            })

    }
}