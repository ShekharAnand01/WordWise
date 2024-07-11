package com.example.dictionary.api

import com.example.dictionary.model.ResultDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("{word}")
    suspend fun getResult(@Path("word") word: String): ResultDataClass
}