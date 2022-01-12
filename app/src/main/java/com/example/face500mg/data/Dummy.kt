package com.example.face500mg.data

import com.google.gson.annotations.SerializedName

class Dummy(


    @SerializedName("status")
    var status  : String,
    @SerializedName("data")
    var data    : Responce,
    @SerializedName("message" )
    var message : String,

    )
data class Responce
    (
    @SerializedName("id" )
    var id : Int
)