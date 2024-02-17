package com.example.chatgptelmajeko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatgptelmajeko.databinding.ActivityMainBinding
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBot.setOnClickListener {
            startActivity(Intent(this,ImageGenerationActivity::class.java))
        }
        binding.chatBot.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }
    }
}