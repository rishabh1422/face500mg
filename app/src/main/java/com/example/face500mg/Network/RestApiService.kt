package com.example.face500mg.Network

import com.example.face500mg.MainActivity
import com.example.face500mg.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RestApiService {
    @GET("customers/id/{cust_id}")
    fun getCustomer(
        @Path("cust_id") id: Int
    ): Call<CustomerResponce?>
    @Multipart
    @POST("customers/find")
    fun getImage(
        @Part target_image: MultipartBody.Part?=null

    ): Call<ImageStatus?>

    @Multipart
    @POST("customers")
    fun setCustomer(
        @Part("reference_id") referenceId: RequestBody,
        @Part("first_name") firstName: RequestBody,
        @Part("middle_name") middleName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("mobile_number") mobileNumber: RequestBody,
        @Part("email_address") emailAddress: RequestBody,
        @Part("udf_1") udf1: RequestBody,
        @Part("udf_2") udf2: RequestBody,
        @Part("udf_3") udf3: RequestBody,
        @Part("udf_4") udf4: RequestBody,
        @Part("udf_5") udf5: RequestBody,
        @Part("timestamp") timestamp: RequestBody,
        @Part image_files: MultipartBody.Part?

    ): Call<Customer?>


    @POST("api/v1/create")
    fun setData(
        @Body params: MainActivity.PassD
    ): Call<Dummy?>
    companion object {

        var retrofitService: RestApiService? = null

        fun getInstance(): RestApiService {

            val BASE_URL = "http://15.207.87.74:8000/"
            val BASE_URL1 = "http://dummy.restapiexample.com/"

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