package com.example.face500mg.ui

import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.face500mg.MainActivity
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.URIPathHelper
import com.example.face500mg.ViewModel.MainViewModel
import com.example.face500mg.ViewModel.MyViewModelFactory
import com.example.face500mg.databinding.ActivitySearchcustomerBinding
import okhttp3.MediaType

import okhttp3.MultipartBody
import okhttp3.RequestBody

import java.io.File

class SearchCustomer : AppCompatActivity() {
    lateinit var binding: ActivitySearchcustomerBinding
    var pickImage=1
    var file22: MultipartBody.Part?=null
    lateinit var imageUri:Uri
    lateinit var viewModel: MainViewModel
    val retrofitService = RestApiService.getInstance()
    val mainRepository = MainRepository(retrofitService)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchcustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)



        setEvent()
    }


    private fun setEvent() {

        binding.camUpload.setOnClickListener {
            val intent = Intent(this, SearchResult::class.java)
            startActivity(intent)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage) {
            imageUri = data?.data!!
            binding.img.setImageURI(imageUri)
            val uriPathHelper = URIPathHelper()
            val filePath = uriPathHelper.getPath(this, imageUri)
            if (filePath!=null) {
                val picture = File(filePath)
//                val requestFile: RequestBody = picture.asRequestBody("image/*".toMediaTypeOrNull())
//                file22 =
//                    MultipartBody.Part.createFormData("picture", picture.name, requestFile)
//                binding.img.setImageURI(imageUri)
//            }
            }
        }
        binding.gallery.setOnClickListener {
            val intent = Intent(this, SearchResult::class.java)
            startActivity(intent)
//            if(file22!=null) {
//                viewModel.setImage(file22)
//
//            }
        }



    }


}