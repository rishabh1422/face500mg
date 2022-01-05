package com.example.face500mg.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.face500mg.MainActivity
import com.example.face500mg.R
import com.example.face500mg.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
        
    }

    private fun setEvent() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}