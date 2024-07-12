package com.example.dictionary.api

import com.example.dictionary.model.DictionaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("{word}")
    suspend fun getResult(@Path("word") word: String): Response<DictionaryResponse>

}