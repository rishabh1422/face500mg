package com.example.face500mg.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.face500mg.MainActivity
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.data.CustomerResponce
import com.example.face500mg.data.Dummy
import com.example.face500mg.data.ImageStatus
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import com.example.face500mg.data.Customer as Customer

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

//    val movieList = MutableLiveData<Customer?>()
    val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val gc = MutableLiveData<CustomerResponce?>()
    val errorMessage = MutableLiveData<String>()
    val sd = MutableLiveData<Dummy?>()
    val setCustomer = MutableLiveData<Customer?>()
    val imagestatus = MutableLiveData<ImageStatus?>()

    fun getCustomer1(cust_id:Int) {

        val response = repository.setAllCustomer(cust_id)
        response.enqueue(object : retrofit2.Callback<CustomerResponce?> {

            override fun onResponse(call: Call<CustomerResponce?>, response: retrofit2.Response<CustomerResponce?>) {
                gc.postValue(response.body())
            }

            override fun onFailure(call: Call<CustomerResponce?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })

    }
  fun setImage(target_image: MultipartBody.Part?)
  {
     val response=repository.getImage(target_image)
      response.enqueue(object :retrofit2.Callback<ImageStatus?>
      {
          override fun onResponse(call: Call<ImageStatus?>, response: Response<ImageStatus?>) {
              showProgress.postValue(true)   // Show prgress bar when api call is active

                  imagestatus.postValue(response.body())


              }


          override fun onFailure(call: Call<ImageStatus?>, t: Throwable) {

              errorMessage.postValue(t.message)
          }
      })




  }

    fun setData(par: MainActivity.PassD) {

        val response = repository.setData(par)
        response.enqueue(object : retrofit2.Callback<Dummy?> {
            override fun onResponse(call: retrofit2.Call<Dummy?>, response: retrofit2.Response<Dummy?>) {
                sd.postValue(response.body())
            }

            override fun onFailure(call: retrofit2.Call<Dummy?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getCustomer(referenceId: RequestBody,
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
                    timestamp: RequestBody,
                    image_files: MultipartBody.Part?) {

        val response = repository.setAllCustomer(referenceId,firstName,middleName,lastName,mobileNumber,emailAddress,udf1,udf2,udf3,udf4,udf5,timestamp,image_files)
        response.enqueue(object : retrofit2.Callback<Customer?> {
            override fun onResponse(call: retrofit2.Call<Customer?>, response: retrofit2.Response<Customer?>) {
                setCustomer.postValue(response.body())
            }

            override fun onFailure(call: retrofit2.Call<Customer?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}





