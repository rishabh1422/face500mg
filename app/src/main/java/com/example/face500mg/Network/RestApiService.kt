package com.example.face500mg.Network

import com.example.face500mg.data.Customer
import com.example.face500mg.data.Data1
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RestApiService
{
    @POST("customers")
    suspend fun setCustomer(
        @Body params: Data1
    ) : Response<Customer>

    companion object {

        private const val BASE_URL = "http://15.207.87.74:8000/"

        var retrofitService: RestApiService? = null
        fun getInstance() : RestApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RestApiService::class.java)
            }
            return retrofitService!!
        }

    }
}