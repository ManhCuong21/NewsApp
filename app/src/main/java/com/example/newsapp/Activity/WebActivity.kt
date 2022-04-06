package com.example.newsapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val url = intent.getStringExtra("url")!!
        binding.webview.loadUrl(url)
    }
}