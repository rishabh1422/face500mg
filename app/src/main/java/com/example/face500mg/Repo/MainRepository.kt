package com.example.face500mg.Repo

import com.example.face500mg.Network.RestApiService
import com.example.face500mg.data.Data2

class MainRepository constructor(private val retrofitService: RestApiService) {

    fun setAllCustomer(params: Data2) = retrofitService.setCustomer(params)

}