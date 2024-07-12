package com.example.dictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.ApiResponse
import com.example.dictionary.model.DictionaryResponse
import com.example.dictionary.repository.MeaningRepository
import kotlinx.coroutines.launch

class MeaningViewmodel() : ViewModel() {
    private val meaningRepository = MeaningRepository()

    private val _result = MutableLiveData<ApiResponse<DictionaryResponse>>()
    val result: LiveData<ApiResponse<DictionaryResponse>>
        get() = _result

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun loadData(word: String) {
        _loading.value=true
        viewModelScope.launch {
            val response = meaningRepository.getData(word)
            if (response.isSuccessful) {
                _result.postValue(ApiResponse.Success(response.body()!!))
            } else {
                _result.postValue(ApiResponse.Error(response.code()))
            }
            _loading.value=false
        }
    }


}