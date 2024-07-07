package com.example.dictionary

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val baseUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dictionaryApi: DictionaryApi = getInstance().create(DictionaryApi::class.java)
}