package com.example.face500mg.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.face500mg.Adapter.SearchResultAdapter
import com.example.face500mg.R
import com.example.face500mg.data.data
import com.example.face500mg.databinding.ActivitySearchResultBinding
import java.security.cert.LDAPCertStoreParameters

class SearchResult : AppCompatActivity() {
    private val search1= ArrayList<data>()
    private lateinit var searchadapter: SearchResultAdapter
    lateinit var binding: ActivitySearchResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    private fun setEvent() {


        searchadapter= SearchResultAdapter(search1)
        val mLayoutManager =GridLayoutManager(this,  2)
        binding.recyMatch.layoutManager = mLayoutManager
        binding.recyMatch.adapter = searchadapter

        result()

    }

    private fun result() {
        var search= data("30% match")
        search1.add(search)
        search = data("55% match")
        search1.add(search)

    }
}