package com.example.face500mg.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.face500mg.Adapter.SearchResultAdapter
import com.example.face500mg.Adapter.SearchResultDataAdapter
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.URIPathHelper
import com.example.face500mg.ViewModel.MainViewModel
import com.example.face500mg.ViewModel.MyViewModelFactory
import com.example.face500mg.data.ImageInfor
import com.example.face500mg.data.data
import com.example.face500mg.databinding.ActivitySearchResultBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream

import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class SearchResult : AppCompatActivity(), SearchResultDataAdapter.OnClick {
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
    val CAMERA_REQUEST = 2
    val MY_CAMERA_PERMISSION_CODE = 10
    var pickImage = 1
    var file22: MultipartBody.Part? = null
    var id1:Int=0
    @RequiresApi(Build.VERSION_CODES.M)
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
    @RequiresApi(Build.VERSION_CODES.M)
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

                    Toast.makeText(this,"Match not found",Toast.LENGTH_SHORT).show()
            }
            else
            {
                binding.recyMatch.layoutManager = LinearLayoutManager(this)
                binding.recyMatch.adapter = SearchResultDataAdapter(list,this)


//                binding.recyMatch.layoutManager = linearLayoutManager
//                adapter = SearchResultDataAdapter(list)
//                binding.recyMatch.adapter = adapter
if(it.data.data.size>0) {
//    binding.card2.visibility=View.VISIBLE
//    binding.card3.visibility=View.VISIBLE
//    Glide.with(this).load(it.data.data[0].tmpurl).into(binding.iv1)
//    Glide.with(this).load(it.data.data[1].tmpurl).into(binding.iv2)
//    binding.custId.text = it.data.data[0].custId.toString()
//    binding.custId2.text = it.data.data[1].custId.toString()
}


        selectPlan = it.data.data
        binding.recyMatch.adapter = SearchResultDataAdapter(selectPlan,this)

               // pictures = it?.data?.data!![0].tmpurl
                Toast.makeText(this, "success"+it.data.message, Toast.LENGTH_SHORT).show()
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
            intent.putExtra("imageUri", imageUri.toString())
            intent.putExtras(extras)
            startActivity(intent)
        }
        binding.iv2.setOnClickListener {
            val intent = Intent(this, CustomerInfoActivity::class.java)
            val extras = Bundle()
            extras.putString("StringVariableName", binding.custId2.text.toString())
            intent.putExtra("imageUri", imageUri.toString())
            intent.putExtras(extras)
//            intent .putExtra("KEY", imageUri);

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
        binding.camera.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
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
            if (resultCode == RESULT_OK) {
                imageUri = data?.data!!
                binding.iv.setImageURI(imageUri)
                val uriPathHelper = URIPathHelper()
                val filePath = uriPathHelper.getPath(this, imageUri)
                if (filePath != null) {
                    val picture = File(filePath)
                    val requestFile: RequestBody =
                        picture.asRequestBody("image/*".toMediaTypeOrNull())
                    file22 =
                        MultipartBody.Part.createFormData(
                            "target_image",
                            picture.getName(),
                            requestFile
                        )
                    binding.iv.setImageURI(imageUri)

                }
            }
            else if (resultCode== RESULT_CANCELED)
            {
                Toast.makeText(this,"Please select image",Toast.LENGTH_SHORT).show()
            }

        }
        when(requestCode) {
            CAMERA_REQUEST -> {
                if (resultCode == RESULT_OK && data != null) {

                    val photo = data.extras!!["data"] as Bitmap?
                    binding.iv.setImageBitmap(photo)
                    val tempUri: Uri = getImageUri(applicationContext, photo)

                    // CALL THIS METHOD TO GET THE ACTUAL PATH

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    val finalFile = File(getRealPathFromURI(tempUri))
//                    binding.decp.text=finalFile.absolutePath
                    if(finalFile!=null) {
                        //binding.decp.text=filePath

//
                        val requestFile = finalFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                        file22 = MultipartBody.Part.createFormData("target_image", finalFile.absolutePath, requestFile)
                    }
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT)
            }
        }

    }

    private fun getRealPathFromURI(tempUri: Uri): String {
        var path = ""
        if (contentResolver != null) {
            val cursor = contentResolver.query(tempUri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path!!

    }

    private fun getImageUri(applicationContext: Context?, photo: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        photo?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(applicationContext?.getContentResolver(), photo, "IMG_" + Calendar.getInstance().getTime(), null)
        return Uri.parse(path)

    }

    override fun onClick(custId: Int) {
        id1=custId
        val intent = Intent(this, CustomerInfoActivity::class.java)
        val extras = Bundle()
        extras.putString("StringVariableName", id1.toString())
        intent.putExtra("imageUri", imageUri.toString())
        intent.putExtras(extras)
        startActivity(intent)

    }
}