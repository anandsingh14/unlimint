package com.anandsingh.joketestapp

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllMovies() : NetworkState<Joke> {
            val response = retrofitService.getAllMovies()
            return if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    NetworkState.Success(responseBody)
                } else {
                    NetworkState.Error(response)
                }
            } else {
                NetworkState.Error(response)
            }
        }

}