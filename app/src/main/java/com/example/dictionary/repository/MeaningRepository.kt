package com.example.dictionary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dictionary.ApiResponse
import com.example.dictionary.api.DictionaryApi
import com.example.dictionary.api.RetrofitInstance
import com.example.dictionary.model.ResultDataClass

class MeaningRepository() {

    private val dictionaryApi = RetrofitInstance.dictionaryApi

    suspend fun getData(word: String): ResultDataClass{
        return dictionaryApi.getResult(word)
    }
}