package com.example.face500mg.data

import android.media.Image
import com.google.gson.annotations.SerializedName

class ImageStatus(
    @SerializedName("data" )
    var data : ImgeData
)
data class ImgeData
    (
    @SerializedName("code")
    var code  : Int,
    @SerializedName("type")
    var type    : String,
    @SerializedName("message")
    var message : String,
    @SerializedName("data")
    var data    : List<ImageInfor>
            )
data class ImageInfor
    (
    @SerializedName("id" )
    var id  : Int,
    @SerializedName("cust_id"  )
    var custId   : Int,
    @SerializedName("filename" )
    var filename : String,
    @SerializedName("tmpurl")
    var tmpurl   : String
            )