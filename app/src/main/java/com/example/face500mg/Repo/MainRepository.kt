package com.example.face500mg.Repo

import com.example.face500mg.Network.RestApiService
import com.example.face500mg.data.Data1

class MainRepository constructor(private val retrofitService: RestApiService) {

    suspend fun setAllCustomer(params:Data1) = retrofitService.setCustomer(params)

}