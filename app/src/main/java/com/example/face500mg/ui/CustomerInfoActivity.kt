package com.example.face500mg.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.face500mg.Adapter.SearchResultAdapter
import com.example.face500mg.Network.RestApiService
import com.example.face500mg.Repo.MainRepository
import com.example.face500mg.ViewModel.MainViewModel
import com.example.face500mg.ViewModel.MyViewModelFactory
import com.example.face500mg.data.data
import com.example.face500mg.databinding.ActivityCustomerInfoBinding

class CustomerInfoActivity : AppCompatActivity() {
    private val search1= ArrayList<data>()
    lateinit var viewModel: MainViewModel
    private lateinit var searchadapter: SearchResultAdapter
    lateinit var binding: ActivityCustomerInfoBinding
    val retrofitService = RestApiService.getInstance()
    val mainRepository = MainRepository(retrofitService)
    lateinit var imageUri: Uri
    //var cust_id:Int=11061

//    val cust_id:Int=11067
    var cust_id: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCustomerInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)


        setEvent()
    }

    private fun setEvent() {

//        cust_id=intent.getStringExtra("cust_Id")
        val extras = intent.extras
        val stringVariableName = extras!!.getString("StringVariableName")
        imageUri = Uri.parse(extras.getString("imageUri"));
        cust_id = stringVariableName!!.toInt()
        binding.iv.setImageURI(imageUri)




//         =intent.getIntExtra("cust_Id",0)

        binding.last.setOnClickListener {
            val intent = Intent(this, SearchCustomer::class.java)
            startActivity(intent)
        }
//        searchadapter= SearchResultAdapter(search1)
//        val mLayoutManager = GridLayoutManager(this,  2)
//        binding.recyMatch.layoutManager = mLayoutManager
//        binding.recyMatch.adapter = searchadapter

        result()

        viewModel.gc.observe(this, androidx.lifecycle.Observer {

            binding.cusName.text="  "+it?.data?.data?.firstName
            Glide.with(this).load(it?.data?.data?.imageFiles?.get(0)?.tmpurl).into(binding.iv1)
            binding.phoneNo.text=it?.data?.data?.mobileNumber
            binding.cusRef.text="  "+it?.data?.data?.referenceId
            binding.emailId.text="  "+it?.data?.data?.emailAddress
            binding.timeSt.text="  "+it?.data?.data?.timestamp



        })
        viewModel.getCustomer1(cust_id)
    }

    private fun result() {
//        var search= data("90% match")
//        search1.add(search)
//        search = data("80% match")
//        search1.add(search)
//        search = data("70% match")
//        search1.add(search)
//        search = data("56% match")
//        search1.add(search)
    }
}