package com.example.face500mg.data

import com.google.gson.annotations.SerializedName
import java.io.File

//data class  Customer (
//    @SerializedName("data" )
//    var data : Statu? = Statu()
//)
    data class Customer(
        @SerializedName("code")
        var code: Int? = null,
        @SerializedName("type")
        var type: String? = null,
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("data")
        var data: Data1? = Data1()
    )
    data class Data1(
        @SerializedName("reference_id")
        var referenceId: String? = null,
        @SerializedName("first_name")
        var firstName: String? = null,
        @SerializedName("middle_name")
        var middleName: String? = null,
        @SerializedName("last_name")
        var lastName: String? = null,
        @SerializedName("mobile_number")
        var mobileNumber: String? = null,
        @SerializedName("email_address")
        var emailAddress: String? = null,
        @SerializedName("udf_1")
        var udf1: String? = null,
        @SerializedName("udf_2")
        var udf2: String? = null,
        @SerializedName("udf_3")
        var udf3: String? = null,
        @SerializedName("udf_4")
        var udf4: String? = null,
        @SerializedName("udf_5")
        var udf5: String? = null,
        @SerializedName("timestamp")
        var timestamp: String? = null,
        @SerializedName("image_files")
        var imageFiles: File?=null
//        var imageFiles   : ArrayList<ImageFiles> = arrayListOf()
    )
data class ImageFiles (
    @SerializedName("id")
    var id: Int?,
    @SerializedName("filename")
    var filename : String? = null,
    @SerializedName("tmpurl")
    var tmpurl   : String? = null,
    @SerializedName("expires" )
    var expires  : String? = null

)


