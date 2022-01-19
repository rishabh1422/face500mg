package com.example.face500mg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.face500mg.databinding.ActivityLoginBinding
import com.example.face500mg.databinding.MatchListBinding

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    private fun setEvent() {

        binding.forgotPass.setOnClickListener {

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.submit.setOnClickListener {
            if (isDatavald()) {
                if (binding.username.text.toString()=="Face500mg"&&binding.password.text.toString()=="India@2022") {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun isDatavald(): Boolean {
        if (binding.username.text!!.isBlank()) {

            binding.username.error = getString(R.string.please_enter_username)
            return false
        } else if (binding.password.text!!.isBlank()) {

            binding.password.error = getString(R.string.please_enter_password)
            return false
        }
        else
        {
            return true
        }

    }

}