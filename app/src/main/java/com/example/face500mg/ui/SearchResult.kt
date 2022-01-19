package com.example.face500mg.ui

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.face500mg.Adapter.SearchResultAdapter
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.R
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.URIPathHelper
import com.example.face500mg.ViewModel.MainViewModel
import com.example.face500mg.ViewModel.MyViewModelFactory
import com.example.face500mg.data.data
import com.example.face500mg.databinding.ActivitySearchResultBinding
import okhttp3.MediaType

import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody

import java.io.File
import java.security.cert.LDAPCertStoreParameters

class SearchResult : AppCompatActivity() {
    private val search1= ArrayList<data>()
    lateinit var imageUri: Uri
    lateinit var viewModel: MainViewModel
    val retrofitService = RestApiService.getInstance()
    val mainRepository = MainRepository(retrofitService)
    private lateinit var searchadapter: SearchResultAdapter
    lateinit var binding: ActivitySearchResultBinding
    var pickImage = 1
    var file22: MultipartBody.Part? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)
        setEvent()
    }

    private fun setEvent() {
//        searchadapter= SearchResultAdapter(search1)
//     val mLayoutManager =GridLayoutManager(this,  2)
//      binding.recyMatch.layoutManager = mLayoutManager
//        binding.recyMatch.adapter = searchadapter

        viewModel.imagestatus.observe(this, Observer {
           it?.data?.data?.get(0)?.tmpurl
            Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()

//            searchadapter.toString()[]
//            Toast(this,"ris",Toast.LENGTH_SHORT).show()
//

        })
        viewModel.setImage(file22)

        binding.status.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), pickImage)

        }
        binding.last.setOnClickListener {
            val intent = Intent(this, SearchCustomer::class.java)
            startActivity(intent)
        }
        binding.moreInfo.setOnClickListener {
            val intent = Intent(this, CustomerInfoActivity::class.java)
            startActivity(intent)
        }


        searchadapter= SearchResultAdapter(search1)
        val mLayoutManager =GridLayoutManager(this,  2)
        binding.recyMatch.layoutManager = mLayoutManager
        binding.recyMatch.adapter = searchadapter

      result()

    }

    private fun result() {
        var search= data("60% match")
        search1.add(search)
        search = data("70% match")
        search1.add(search)
        search = data("80% match")
        search1.add(search)
        search = data("85% match")
        search1.add(search)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage) {
            imageUri = data?.data!!
            binding.iv.setImageURI(imageUri)
            val uriPathHelper = URIPathHelper()
            val filePath = uriPathHelper.getPath(this, imageUri)
            if (filePath != null) {
                val picture = File(filePath)
//                val requestFile: RequestBody = picture.asRequestBody("image/*".toMediaTypeOrNull())
//                file22 =
//                    MultipartBody.Part.createFormData("picture", picture.getName(), requestFile)
//                binding.img.setImageURI(imageUri)
            }
        }


    }
}