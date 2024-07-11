package com.example.dictionary.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val baseUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/"

    val dictionaryApi: DictionaryApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(DictionaryApi::class.java)

}