package com.example.face500mg.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.data.Data1
import com.example.face500mg.data.Data2
import com.example.face500mg.data.Dummy

//import com.example.face500mg.data.Statu
import kotlinx.coroutines.*
import okhttp3.Response
import retrofit2.Call
import javax.security.auth.callback.Callback
import com.example.face500mg.data.Customer as Customer

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val movieList = MutableLiveData<Dummy>()
    val errorMessage = MutableLiveData<String>()

    fun setCustomer(par: Data2) {

        val response = repository.setAllCustomer(par)
        response.enqueue(object : retrofit2.Callback<Dummy?> {

            override fun onResponse(call: Call<Dummy?>, response: retrofit2.Response<Dummy?>) {
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<Dummy?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}



