package com.example.face500mg

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.net.http.RequestQueue
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.face500mg.ImageProcess.context
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.ViewModel.MainViewModel
import com.example.face500mg.ViewModel.MyViewModelFactory
import com.example.face500mg.databinding.ActivityMainBinding
import com.example.face500mg.ui.SearchCustomer
import com.google.android.material.navigation.NavigationView
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Environment
import android.provider.DocumentsContract

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream
import okhttp3.FormBody;

import okhttp3.OkHttpClient;
import androidx.core.app.ActivityCompat.startActivityForResult

import androidx.core.app.ActivityCompat
import com.example.face500mg.ui.SearchResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private var select_uri: String? = null
    lateinit var viewModel: MainViewModel
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    val retrofitService = RestApiService.getInstance()
    val mainRepository = MainRepository(retrofitService)
    val MY_CAMERA_PERMISSION_CODE = 1
    val CAMERA_REQUEST = 2
    var encodedImage: String?=null
    var file22: MultipartBody.Part? = null
    val pickImage = 100
    var path1 = "/storage/emulated/0/Download/Corrections 6.jpg"
//    private var image = 1

    var pinCode: String? = null
    lateinit var requestname: RequestBody

    // creating a variable for request queue.
    private val mRequestQueue: RequestQueue? = null
    private lateinit var imageUri: Uri
    var cust_id: Int = 11067


    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)




        setEvent()


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setEvent() {
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this,"Network failed, Please try again",Toast.LENGTH_SHORT).show()

        })

        viewModel.setCustomer.observe(this, {
            it?.data
            if (it == null) {
                Toast.makeText(this, "Something went wong", Toast.LENGTH_LONG).show()
            } else {

                val intent = Intent(this, SearchResult::class.java)
                startActivity(intent)
                Toast.makeText(this, "Success" + it?.data?.data?.id, Toast.LENGTH_LONG).show()
            }

        })
        binding.last.setOnClickListener {
            val intent = Intent(this, SearchResult::class.java)
            startActivity(intent)
        }
//        val file=File("")


//        binding.camUpload.setOnClickListener {
////
//
//
//            ImageProcess.getInstanced(this).selectImage(this)
////
//        }
        viewModel.gc.observe(this, Observer {
            Toast.makeText(
                this,
                "sucess Rechived" + it?.data?.data?.emailAddress,
                Toast.LENGTH_LONG
            ).show()
            Log.d("TAG", "rishabh" + it?.data?.data?.emailAddress)


        })
        viewModel.getCustomer1(cust_id)

        viewModel.sd.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, "sucess" + it?.data?.id, Toast.LENGTH_LONG).show()


//            it?.data
//            Log.d(TAG, "ruishabh"+it?.data)

        })
//        viewModel.movieList.observe(this, Observer {
//            if (it == null) {
//
//                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
//            }
//        })
        binding.gallery.setOnClickListener {
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
        binding.camUpload.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }


        binding.submit.setOnClickListener {
            if (isDataValid()) {

                val name = binding.name.text.toString()
                val mid_name = "idm"
                val lastName = binding.lastName.text.toString()
                val mobileNumber = binding.number.text.toString()
                val emailAddress = binding.emailId.text.toString()
                val udf1 = ""
                val udf2 = ""
                val udf3 = ""
                val udf4 = ""
                val udf5 = ""
                val timestamp = "2022-01-04 05:36:36"
                val ref = binding.address.text.toString()
                val _ref: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    ref
                )

                val r_name: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    name
                )
                val midname: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    mid_name)
                val last_requestname: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    lastName)

                val mob_requestname: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), mobileNumber
                )
                val email_requestname: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), emailAddress
                )
                val _udf1: RequestBody= RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), udf1
                )
                val _udf2: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), udf2
                )
                val _udf3: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), udf3
                )
                val _udf4: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), udf4
                )
                val _udf5: RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), udf5
                )
                val _time:RequestBody = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), timestamp
                )
   //             Thread {
//                    val jSONObject = JSONObject()
//                    val arrayList = ArrayList<Any>()
//                    try {
//                        jSONObject.put("reference_id", ref)
//                        arrayList.add("reference_id")
//                        jSONObject.put("first_name", name)
//                        arrayList.add("first_name")
//                        jSONObject.put("middle_name", mid_name)
//                        arrayList.add("middle_name")
//                        jSONObject.put("last_name", "idm")
//                        arrayList.add("last_name")
//                        jSONObject.put("mobile_number", mobileNumber)
//                        arrayList.add("mobile_number")
//                        jSONObject.put("email_address", emailAddress)
//                        arrayList.add("email_address")
//                        jSONObject.put("udf_1", "")
//                        arrayList.add("udf_1")
//                        jSONObject.put("udf_2", "")
//                        arrayList.add("udf_2")
//
//                        jSONObject.put("udf_3", "")
//                        arrayList.add("udf_3")
//                        jSONObject.put("udf_4", "")
//                        arrayList.add("udf_4")
//                        jSONObject.put("udf_5", "")
//                        arrayList.add("udf_5")
//
//                        jSONObject.put("timestamp", "2022-01-04 05:36:36")
//                        arrayList.add("timestamp")
//                        jSONObject.put("image_files", file22)
//                        arrayList.add("image_files")
//
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//                    val printStream: PrintStream = System.out
//                    val sb3 = StringBuilder()
//                    sb3.append("jsonParams ")
//                    sb3.append(jSONObject.toString())
//                    printStream.println(sb3.toString())
//                    val postResponse: String = WebUtils.getPostResponse(
//                        this@MainActivity,
//                        arrayList,
//                        jSONObject,
//                        "http://15.207.87.74:8000/customers",
//                        "",
//                        ""
//                    )
//                    val printStream2: PrintStream = System.out
//                    val sb4 = StringBuilder()
//                    sb4.append("logingresponcr")
//                    sb4.append(postResponse)
//                    printStream2.println(sb4.toString())
//                }.start()
//                Log.d("TAG", "im" + file22)

                viewModel.getCustomer(_ref,
                    r_name, midname, last_requestname, mob_requestname,
                    email_requestname,
                   _udf1, _udf2, _udf3, _udf4, _udf5,
                   _time,
                    file22
                )

                if (file22!=null) {
//
                    Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
        }




        binding.img.setOnClickListener {
            val params = PassD(
                name = "Rishsabjh",
                salary = "7",
                age = "6"
            )
            viewModel.setData(params)

        }


//        viewModel.gc.observe(this, Observer {
//         it.data
//        })
//        viewModel.getCustomer(cust_id)












    }

    private fun isDataValid(): Boolean {


            if (binding.name.text!!.isBlank()) {

                binding.name.error = getString(R.string.please_enter_name)


                return false
            }
        else if (binding.lastName.text!!.isBlank()) {

            binding.lastName.error = getString(R.string.please_enter_last)
            return false
        }
            else if (binding.number.text!!.length < 10) {

           binding.number.error = getString(R.string.please_enter_mobile_number)
                return false
            }
            else if (binding.emailId.text!!.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(binding.emailId.text.toString())
                    .matches()) {
          binding.emailId.error = getString(R.string.please_enter_valid_email)
                return false
            }
            if (binding.address.text!!.isBlank()) {
           binding.address.error = getString(R.string.please_enter_address)
                return false
            }
            else
            {
                return true
            }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CAMERA_REQUEST -> {
                if (resultCode == RESULT_OK && data != null) {
                    binding.img.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT)
            }
        }
//        if (resultCode== CAMERA_REQUEST)
//        {
//           binding.img.setImageBitmap(data?.extras?.get("data") as Bitmap)
//
//        }
        if (requestCode === pickImage) {
            imageUri = data?.data!!
            binding.img.setImageURI(imageUri)
//            val imageStream: InputStream? = contentResolver.openInputStream(imageUri)
//            val selectedImage = BitmapFactory.decodeStream(imageStream)
//           encodedImage = encodeImage(selectedImage)

            val uriPathHelper = URIPathHelper()
            val filePath = uriPathHelper.getPath(this, imageUri)
            if(filePath!=null) {
                //binding.decp.text=filePath
                val file = File(filePath)
//                val requestBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

                file22 = MultipartBody.Part.createFormData("image_files", file.name, requestFile)
            }
//            binding.img.setImageURI(imageUri)
//
//
//            imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            val cursor: Cursor?
//            val columnIndexData: Int
//            val listOfAllImages: MutableList<String> = mutableListOf()
//            val projection = arrayOf(MediaStore.MediaColumns.DATA)
//            var absolutePathOfImage: String
//            cursor = this.contentResolver.query(imageUri, projection, null, null, null)
//            if (cursor != null) {
//                columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//                while (cursor.moveToNext()) {
//                    absolutePathOfImage = cursor.getString(columnIndexData)
//                    listOfAllImages.add(absolutePathOfImage)
//                }
//                cursor.close()
//            }

//            val file = File(imageUri!!.path) //create path from uri
//
//            val split = file.path.split(":").toTypedArray() //split the path.
//
//            val filePath = split[1] //assign it to a string(your choice).
//            val requestFile: RequestBody =
//                            RequestBody.create(MediaType.parse("multipart/form-data"),file)
//            file22 =
//                            MultipartBody.Part.createFormData("image", file.name, requestFile)




        }
    }



    private fun getPath(imageUri: Uri): String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(imageUri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result

    }
//        try {
//
//                if (requestCode === pickImage) {
//
//                    imageUri = data?.data!!
//                    binding.img.setImageURI(imageUri)
//
//                    // Get the path from the Uri
//                    val path: String = getPathFromURI(imageUri)
//                    if (path != null) {
//                        val f = File(path)
//                        imageUri = Uri.fromFile(f)
//                        val requestFile: RequestBody =
//                            RequestBody.create(MediaType.parse("multipart/form-data"), f)
//                        file22 =
//                            MultipartBody.Part.createFormData("image", f.name, requestFile)
//
//                    }
//                    // Set the image in ImageView
//
//
//
//            }
//        } catch (e: Exception) {
//            Log.e("FileSelectorActivity", "File select error", e)
//        }
//
//    }

    private fun getPathFromURI(imageUri: Uri): String {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(imageUri, proj, null, null, null)
        if (cursor?.moveToFirst()!!) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor?.getString(column_index)
        }
        cursor?.close()
        return res!!

    }












    data class PassD(
        @SerializedName("name")
        var name   : String,
        @SerializedName("salary")
        var salary : String,
        @SerializedName("age")
        var age    : String
    )




}

class URIPathHelper {
    fun getPath(context: Context, uri: Uri): String? {
        val isKitKatorAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKatorAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = uri?.let { context.getContentResolver().query(it, projection, selection, selectionArgs,null) }
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

}
