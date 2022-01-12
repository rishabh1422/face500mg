package com.example.face500mg.data

import com.google.gson.annotations.SerializedName

data class CustomerResponce(
    @SerializedName("data" )
    var data : Responce1

)
data class Responce1
    (

    @SerializedName("code") var code    : Int,
    @SerializedName("type"    ) var type    : String,
    @SerializedName("message" ) var message : String,
    @SerializedName("data"    ) var data    : CustomerDetails
            )
data class CustomerDetails(

    @SerializedName("id") var id : Int,
    @SerializedName("reference_id") var referenceId  : String,
    @SerializedName("first_name" ) var firstName    : String,
    @SerializedName("middle_name") var middleName   : String,
    @SerializedName("last_name") var lastName     : String,
    @SerializedName("mobile_number") var mobileNumber : String,
    @SerializedName("email_address") var emailAddress : String,
    @SerializedName("udf_1") var udf1 : String,
    @SerializedName("udf_2") var udf2 : String,
    @SerializedName("udf_3") var udf3 : String,
    @SerializedName("udf_4") var udf4 : String,
    @SerializedName("udf_5") var udf5 : String,
    @SerializedName("timestamp") var timestamp : String,
    @SerializedName("created_on") var createdOn    : String,
    @SerializedName("image_files" ) var imageFiles   : List<ImageFiles1>
)

data class ImageFiles1(

    @SerializedName("id" ) var id       : Int,
    @SerializedName("cust_id" ) var custId   : Int,
    @SerializedName("filename") var filename : String,
    @SerializedName("tmpurl") var tmpurl   : String,
    @SerializedName("expires") var expires  : String

)