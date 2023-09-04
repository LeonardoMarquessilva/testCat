package com.example.testcat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.testcat.data.network.RetrofitInstance
import com.example.testcat.data.response.Result
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {

    private val retrofit = RetrofitInstance.retrofit
    val resultCat = MutableLiveData<Result>()
    val errorResultCat = MutableLiveData<String>()
    val loadingResultCat = MutableLiveData<Boolean>()

    fun getImageCat() = viewModelScope.launch {
        loadingResultCat.value = true
        if (retrofit.getImageCats().isSuccessful) {
            retrofit.getImageCats().body()?.let { listResult ->
                loadingResultCat.value = false
                resultCat.value = listResult
            }
        } else {
            loadingResultCat.value = false
            errorResultCat.value = retrofit.getImageCats().code().toString()
        }
    }

}