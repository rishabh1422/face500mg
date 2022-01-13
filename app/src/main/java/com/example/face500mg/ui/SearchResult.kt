package com.example.face500mg.ui

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.face500mg.Adapter.SearchResultAdapter
import com.example.face500mg.R
import com.example.face500mg.data.data
import com.example.face500mg.databinding.ActivitySearchResultBinding
import okhttp3.Request
import java.security.cert.LDAPCertStoreParameters

class SearchResult : AppCompatActivity() {
    private val search1= ArrayList<data>()
    private lateinit var searchadapter: SearchResultAdapter
    lateinit var binding: ActivitySearchResultBinding
    var pickImage=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    private fun setEvent() {
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
        fun getPincode() {


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
}