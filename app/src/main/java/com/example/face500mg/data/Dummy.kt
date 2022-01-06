package com.example.face500mg.data

import com.google.gson.annotations.SerializedName

class Dummy(

    @SerializedName("status" )
    var status : String? = null,
    @SerializedName("data"   )
    var data   : Data2?   = Data2()

)

data class Data2 (

    @SerializedName("name")
    var name   : String? = null,
    @SerializedName("salary")
    var salary : String? = null,
    @SerializedName("age")
    var age    : String? = null,
    @SerializedName("id")
    var id     : Int?    = null

)