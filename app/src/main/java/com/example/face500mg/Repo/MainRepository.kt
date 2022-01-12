package com.example.face500mg.Repo

import com.example.face500mg.MainActivity
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.data.Data1
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainRepository constructor(private val retrofitService: RestApiService) {

    fun getImage(target_image: MultipartBody.Part?) = retrofitService.getImage(target_image)
    fun setAllCustomer(
        referenceId: RequestBody,
        firstName: RequestBody,
        middleName: RequestBody,
        lastName: RequestBody,
        mobileNumber: RequestBody,
        emailAddress: RequestBody,
        udf1: RequestBody,
        udf2: RequestBody,
        udf3: RequestBody,
        udf4: RequestBody,
        udf5: RequestBody,
        timestamp:RequestBody,
        image_files: MultipartBody.Part?)
    = retrofitService.setCustomer(referenceId,
        firstName,middleName,lastName,
        mobileNumber,emailAddress,
        udf1,udf2,udf3,udf4,udf5,timestamp,image_files)
    fun setAllCustomer(cust_id: Int) = retrofitService.getCustomer(cust_id)
    fun setData(params: MainActivity.PassD) = retrofitService.setData(params)

}