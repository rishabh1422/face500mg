package com.example.face500mg.ViewModel

import android.media.MediaMetadataRetriever
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.data.Customer
import com.example.face500mg.data.Data1
import com.example.face500mg.data.data
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val customelist = MutableLiveData<Customer>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()
//    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        onError("Exception handled: ${throwable.localizedMessage}")
//    }


    fun setCustomer(params: Data1) {
        job = CoroutineScope(Dispatchers.IO /*+ exceptionHandler*/).launch {

            val response = mainRepository.setAllCustomer(params)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    customelist.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}