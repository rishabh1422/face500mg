package com.example.face500mg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.face500mg.databinding.ActivityMainBinding

import android.content.Intent

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.ViewModel.MainViewModel
import com.example.face500mg.ViewModel.MyViewModelFactory
import com.example.face500mg.data.Data2
import com.example.face500mg.ui.SearchCustomer
import com.google.android.material.navigation.NavigationView
import org.json.JSONException
import org.json.JSONObject
import java.io.PrintStream


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    val retrofitService = RestApiService.getInstance()
    val mainRepository = MainRepository(retrofitService)
    val MY_CAMERA_PERMISSION_CODE=1
    val CAMERA_REQUEST=2
    val pickImage=100
    private lateinit var imageUri: Uri

    lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)




        setEvent()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setEvent() {
        viewModel.movieList.observe(this, Observer {
            if (it==null)
            {
                Toast.makeText(this,it?.status,Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            }
        })



//        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
//        binding.dl.addDrawerListener(actionBarToggle)
//
//        // Display the hamburger icon to launch the drawer
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
//        actionBarToggle.syncState()
//
//
//        // Call findViewById on the NavigationView
//
//
//        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
//        binding.nvFace.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.nav_account -> {
//                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_logout -> {
//                    Toast.makeText(this, "People", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_settings -> {
//                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> {
//                    false
//                }
//            }
//        }




        binding.camUpload.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }
        binding.gallery.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), pickImage)
//                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//                startActivityForResult(gallery, pickImage)
        }
        binding.submit.setOnClickListener {
            if(isDataValid())
            {

                val params= Data2(
                    name = "Rishabh",
                    salary = "2",
                    age = "43",
                    id = 838
//                    referenceId = binding.lastName.text.toString(),
//                    firstName = binding.name.text.toString(),
//                    middleName = binding.midName.text.toString(),
//                    lastName = binding.lastName.text.toString(),
//                    mobileNumber =binding.number.text.toString(),
//                    emailAddress = binding.emailId.text.toString(),
//                    udf1 = "",
//                    udf2 = "",
//                    udf3 = "",
//                    udf4 = "",
//                    udf5 = "",
//                    timestamp = "2022-01-04 05:36:36",
//                    imageFiles = filesDir
//                    imageFiles = arrayListOf(
//                        ImageFiles(
//                            id = 70,
//                            filename="J",
//                            tmpurl="https://idreambiz-face-compare.s3.amazonaws.com/vgg/79-profile.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAV2TMBLWR6IUB2PG2%2F20220105%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Date=20220105T102340Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=512c16c095411b720fa185d190f56bb17ba24083bf3dae01f5a3cd0b25b84aea",
//                            expires = null
//
//                        )
//                    )


                )
                viewModel.setCustomer(params)
                Log.d("x", "rishabh"+viewModel.setCustomer(params))

                val intent = Intent(this, SearchCustomer::class.java)
                startActivity(intent)
            }
        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.img.setImageBitmap(imageBitmap)
        }
        if (requestCode==pickImage )
        {
             imageUri= data?.data!!
             binding.img.setImageURI(imageUri)
        }
    }

    private fun isDataValid(): Boolean {
//
//        if (binding.name.text!!.isBlank()) {
//
//            binding.name.error = getString(R.string.please_enter_name)
//            return false
//        } else if (binding.number.text!!.length < 10) {
//
//            binding.number.error = getString(R.string.please_enter_mobile_number)
//            return false
//        }
//        else if (binding.emailId.text!!.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(binding.emailId.text.toString())
//                .matches()) {
//            binding.emailId.error = getString(R.string.please_enter_valid_email)
//            return false
//        }
       if (binding.address.text!!.isBlank()) {
            binding.address.error = getString(R.string.please_enter_address)
            return false
        }
        else
        {
            return true
        }

    }

}