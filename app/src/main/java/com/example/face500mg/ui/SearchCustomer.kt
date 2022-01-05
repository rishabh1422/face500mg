package com.example.face500mg.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.face500mg.MainActivity
import com.example.face500mg.databinding.ActivitySearchcustomerBinding

class SearchCustomer : AppCompatActivity() {
    lateinit var binding: ActivitySearchcustomerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchcustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    private fun setEvent() {
        binding.gallery.setOnClickListener {
            val intent = Intent(this, SearchResult::class.java)
            startActivity(intent)
        }

    }
}