package com.example.dictionary.repository

import com.example.dictionary.api.RetrofitInstance
import com.example.dictionary.model.DictionaryResponse
import retrofit2.Response

class MeaningRepository() {

    private val dictionaryApi = RetrofitInstance.dictionaryApi

    suspend fun getData(word: String): Response<DictionaryResponse> {
        return dictionaryApi.getResult(word)
    }

}