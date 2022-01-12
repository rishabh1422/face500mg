package com.example.face500mg

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.PrintStream
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.provider.DocumentsContract
import android.view.View
import com.example.face500mg.ImageProcess.context
import com.example.face500mg.data.data

import android.graphics.Bitmap













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
        binding.last.setOnClickListener {
            val intent = Intent(this, SearchCustomer::class.java)
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

        viewModel.setCustomer.observe(this, {
            if (it == null) {
                Toast.makeText(this, "Something went wong", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "" + it?.data?.data?.id, Toast.LENGTH_LONG).show()
            }

        })
        viewModel.sd.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, "sucess" + it?.data?.id, Toast.LENGTH_LONG).show()


//            it?.data
//            Log.d(TAG, "ruishabh"+it?.data)

        })
        viewModel.movieList.observe(this, Observer {
            if (it == null) {

                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            }
        })
        binding.gallery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
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
                    MediaType.parse("text/plain"), ref
                )
                val r_name: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), name
                )
                val midname: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), mid_name
                )
                val last_requestname: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), lastName
                )
                val mob_requestname: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), mobileNumber
                )
                val email_requestname: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), emailAddress
                )
                val _udf1: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), udf1
                )
                val _udf2: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), udf2
                )
                val _udf3: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), udf3
                )
                val _udf4: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), udf4
                )
                val _udf5: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), udf5
                )
                val _time: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"), timestamp
                )
                Thread {
                    val jSONObject = JSONObject()
                    val arrayList = ArrayList<Any>()
                    try {
                        jSONObject.put("reference_id", ref)
                        arrayList.add("reference_id")
                        jSONObject.put("first_name", name)
                        arrayList.add("first_name")
                        jSONObject.put("middle_name", mid_name)
                        arrayList.add("middle_name")
                        jSONObject.put("last_name", "idm")
                        arrayList.add("last_name")
                        jSONObject.put("mobile_number", mobileNumber)
                        arrayList.add("mobile_number")
                        jSONObject.put("email_address", emailAddress)
                        arrayList.add("email_address")
                        jSONObject.put("udf_1", "")
                        arrayList.add("udf_1")
                        jSONObject.put("udf_2", "")
                        arrayList.add("udf_2")

                        jSONObject.put("udf_3", "")
                        arrayList.add("udf_3")
                        jSONObject.put("udf_4", "")
                        arrayList.add("udf_4")
                        jSONObject.put("udf_5", "")
                        arrayList.add("udf_5")

                        jSONObject.put("timestamp", "2022-01-04 05:36:36")
                        arrayList.add("timestamp")
                        jSONObject.put("image_files", file22)
                        arrayList.add("image_files")

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    val printStream: PrintStream = System.out
                    val sb3 = StringBuilder()
                    sb3.append("jsonParams ")
                    sb3.append(jSONObject.toString())
                    printStream.println(sb3.toString())
                    val postResponse: String = WebUtils.getPostResponse(
                        this@MainActivity,
                        arrayList,
                        jSONObject,
                        "http://15.207.87.74:8000/customers",
                        "",
                        ""
                    )
                    val printStream2: PrintStream = System.out
                    val sb4 = StringBuilder()
                    sb4.append("logingresponcr")
                    sb4.append(postResponse)
                    printStream2.println(sb4.toString())
                }.start()
                Log.d("TAG", "im" + file22)

//                viewModel.getCustomer(
//                    referenceId = _ref,
//                    firstName = r_name,
//                    middleName = midname,
//                    lastName = last_requestname,
//                    mobileNumber = mob_requestname,
//                    emailAddress = email_requestname,
//                    udf1 = _udf1,
//                    udf2 =_udf2,
//                    udf3 = _udf3,
//                    udf4 = _udf4,
//                    udf5 = _udf5,
//                    timestamp = _time,
//                    image_files = file22
//                )


//                        referenceId = binding.address.text.toString(),
//                        firstName = binding.name.text.toString(),
//                        middleName = binding.midName.text.toString(),
//                        lastName = binding.lastName.text.toString(),
//                        mobileNumber =binding.number.text.toString(),
//                        emailAddress = binding.emailId.text.toString(),
//                        udf1 = "",
//                        udf2 = "",
//                        udf3 = "",
//                        udf4 = "",
//                        udf5 = "",
//                        timestamp = "2022-01-04 05:36:36",


//                viewModel.setCustomer(params)
//                Log.d("x", "rishabh"+viewModel.setCustomer(params))
                if (file22!=null) {

                    val intent = Intent(this, SearchCustomer::class.java)
                    startActivity(intent)
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

                binding.number.error = getString(R.string.please_enter_name)


                return false
            }
        else if (binding.lastName.text!!.isBlank()) {

            binding.number.error = getString(R.string.please_enter_last)
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
                if (resultCode == Activity.RESULT_OK && data != null) {
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

            val uriPathHelper = URIPathHelper()
            val filePath = uriPathHelper.getPath(this, imageUri)
            if(filePath!=null) {
                val file = File(filePath)

                val requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
                file22 =
                    MultipartBody.Part.createFormData("image", file.name, requestFile)
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
