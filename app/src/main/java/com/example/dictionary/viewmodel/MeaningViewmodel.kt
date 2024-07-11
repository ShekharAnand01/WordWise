package com.example.dictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.ApiResponse
import com.example.dictionary.model.ResultDataClass
import com.example.dictionary.repository.MeaningRepository
import kotlinx.coroutines.launch

class MeaningViewmodel() : ViewModel() {
    private val meaningRepository = MeaningRepository()

    private val _result = MutableLiveData<ResultDataClass>()
    val result: LiveData<ResultDataClass>
        get() = _result

    fun loadData(word: String) {
        viewModelScope.launch {
            val response = meaningRepository.getData(word)
            _result.postValue(response)
        }

    }

}