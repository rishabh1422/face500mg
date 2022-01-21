package com.example.face500mg.ui

import android.app.DownloadManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.face500mg.Adapter.SearchResultAdapter
import com.example.face500mg.Adapter.SearchResultDataAdapter
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.R
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.URIPathHelper
import com.example.face500mg.ViewModel.MainViewModel
import com.example.face500mg.ViewModel.MyViewModelFactory
import com.example.face500mg.data.ImageInfor
import com.example.face500mg.data.data
import com.example.face500mg.databinding.ActivitySearchResultBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

import java.io.File
import java.lang.Exception
import java.security.cert.LDAPCertStoreParameters

class SearchResult : AppCompatActivity() {
    private val search1= ArrayList<data>()
    private var progressBar: ProgressBar? = null
    private var pictures: List<ImageInfor> = ArrayList()
    lateinit var imageUri: Uri
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: SearchResultDataAdapter
    var list: ArrayList<ImageInfor> = ArrayList()
    lateinit var viewModel: MainViewModel
    private lateinit var selectPlan: List<ImageInfor>
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
        viewModel.showProgress.observe(this, Observer {
            progressBar?.visibility=
                if (it)
            {View.VISIBLE }
            else
                View.GONE
        })

       // binding.recyMatch.adapter = adapter
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this,"Network failed, Please try again",Toast.LENGTH_SHORT).show()

        })
        viewModel.imagestatus.observe(this, Observer {
            if(it==null)
            {

                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
            else
            {

//                binding.recyMatch.layoutManager = linearLayoutManager
//                adapter = SearchResultDataAdapter(list)
//                binding.recyMatch.adapter = adapter
if(it.data.data.size>0) {
    Glide.with(this).load(it.data.data[0].tmpurl).into(binding.iv1)
    Glide.with(this).load(it.data.data[1].tmpurl).into(binding.iv2)
    binding.custId.text = it.data.data[0].custId.toString()
    binding.custId2.text = it.data.data[1].custId.toString()
}


        selectPlan = it.data.data
        binding.recyMatch.adapter = SearchResultDataAdapter(selectPlan)

               // pictures = it?.data?.data!![0].tmpurl

                Toast.makeText(this, "success"+it.data, Toast.LENGTH_SHORT).show()
            }

        })

        binding.status.setOnClickListener {
            if (file22!=null)
            {
                Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show()
            }
            viewModel.setImage(file22)
        }


        binding.iv1.setOnClickListener {
            val intent = Intent(this, CustomerInfoActivity::class.java)
            val extras = Bundle()
            extras.putString("StringVariableName", binding.custId.text.toString())
            intent.putExtras(extras)

            startActivity(intent)
        }
        binding.iv2.setOnClickListener {
            val intent = Intent(this, CustomerInfoActivity::class.java)
            val extras = Bundle()
            extras.putString("StringVariableName", binding.custId2.text.toString())
            intent.putExtras(extras)

            startActivity(intent)
        }

        binding.cusName.setOnClickListener {
            try {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        pickImage
                    )
                } else {
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, pickImage)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        binding.last.setOnClickListener {
            val intent = Intent(this, SearchCustomer::class.java)
            startActivity(intent)
        }
        binding.moreInfo.setOnClickListener {
            val intent = Intent(this, CustomerInfoActivity::class.java)
            startActivity(intent)
        }


//        searchadapter= SearchResultAdapter(search1)
//        val mLayoutManager =GridLayoutManager(this,  2)
//        binding.recyMatch.layoutManager = mLayoutManager
//        binding.recyMatch.adapter = searchadapter
//
//      result()

    }

//    private fun result() {
//        var search= data("60% match")
//        search1.add(search)
//        search = data("70% match")
//        search1.add(search)
//        search = data("80% match")
//        search1.add(search)
//        search = data("85% match")
//        search1.add(search)
//
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage) {
            imageUri = data?.data!!
            binding.iv.setImageURI(imageUri)
            val uriPathHelper = URIPathHelper()
            val filePath = uriPathHelper.getPath(this, imageUri)
            if (filePath != null) {
                val picture = File(filePath)
                val requestFile: RequestBody = picture.asRequestBody("image/*".toMediaTypeOrNull())
                file22 =
                    MultipartBody.Part.createFormData("target_image", picture.getName(), requestFile)
                binding.iv.setImageURI(imageUri)

            }
        }


    }
}