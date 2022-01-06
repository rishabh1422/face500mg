package com.example.face500mg.Network

import com.example.face500mg.data.Customer
import com.example.face500mg.data.Data2
import com.example.face500mg.data.Dummy
import retrofit2.Call
//import com.example.face500mg.data.Statu
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RestApiService {
    @POST("create")
    fun setCustomer(
        @Body params: Data2
    ): Call<Dummy?>

    companion object {


        var retrofitService: RestApiService? = null

        fun getInstance(): RestApiService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://dummy.restapiexample.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RestApiService::class.java)
            }
            return retrofitService!!
        }
    }
}