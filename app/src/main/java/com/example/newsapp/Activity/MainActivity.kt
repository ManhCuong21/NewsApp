package com.example.newsapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.NewsClient
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.fragment.*
import com.example.newsapp.fragment.adapter.ViewPagerAdapter
import kotlinx.coroutines.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpTabs()

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val newsResponse = async {
                    NewsClient.news.getNews(
                        "de",
                        "20",
                        "b574a5aaa3bf44ca9b5320f6447a107a"
                    )
                }.await()

                val categoryNewsResponse = async {
                    NewsClient.news.getCategoryNews(
                        "de",
                        "business",
                        "20",
                        "b574a5aaa3bf44ca9b5320f6447a107a"
                    )
                }.await()

                if (newsResponse.isSuccessful && categoryNewsResponse.isSuccessful) {

                }
            }
        } catch (e: IOException) {
            Log.e("AAA", "$e")
        }
    }

    private fun setUpTabs() {
        val apdapter = ViewPagerAdapter(supportFragmentManager)
        apdapter.addFragment(GeneralFragment(), "General")
        apdapter.addFragment(BusinessFragment(), "Business")
        apdapter.addFragment(EntertainmentFragment(), "Entertainment")
        apdapter.addFragment(TechnologyFragment(), "Technology")
        apdapter.addFragment(ScienceFragment(), "Science")
        apdapter.addFragment(SportsFragment(), "Sports")
        apdapter.addFragment(HealthFragment(), "Health")

        binding.viewPager.adapter = apdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }
}