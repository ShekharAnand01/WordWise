package com.example.dictionary

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("{word}")
    suspend fun getResult(@Path("word") word : String) :  Response<List<ResultDataClass>>
}