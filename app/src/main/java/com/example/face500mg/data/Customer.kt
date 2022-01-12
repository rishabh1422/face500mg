package com.example.face500mg.data

import com.google.gson.annotations.SerializedName
import java.io.File

data class  Customer (
    @SerializedName("data" )
    var data : Statu
)
    data class Statu(
        @SerializedName("code")
        var code: Int,
        @SerializedName("type")
        var type: String,
        @SerializedName("message")
        var message: String,
        @SerializedName("data")
        var data: Data1
    )
    data class Data1(
        @SerializedName("id")
        var id: Int,
        @SerializedName("reference_id")
        var referenceId: String,
        @SerializedName("first_name")
        var firstName: String,
        @SerializedName("middle_name")
        var middleName: String,
        @SerializedName("last_name")
        var lastName: String,
        @SerializedName("mobile_number")
        var mobileNumber: String,
        @SerializedName("email_address")
        var emailAddress: String,
        @SerializedName("udf_1")
        var udf1: String,
        @SerializedName("udf_2")
        var udf2: String,
        @SerializedName("udf_3")
        var udf3: String,
        @SerializedName("udf_4")
        var udf4: String,
        @SerializedName("udf_5")
        var udf5: String,
        @SerializedName("timestamp")
        var timestamp: String,
        @SerializedName("image_files")
        var imageFiles: List<ImageFiles>
    )
data class ImageFiles (
    @SerializedName("id")
    var id: Int,
    @SerializedName("filename")
    var filename : String,
    @SerializedName("tmpurl")
    var tmpurl   : String,
    @SerializedName("expires" )
    var expires  : String

)


