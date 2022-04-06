package com.example.newsapp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.Activity.MainActivity
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.News
import com.example.newsapp.NewsClient
import com.example.newsapp.adapter.MainMenuAdapter
import com.example.newsapp.adapter.OtherNewsAdapter
import com.example.newsapp.adapter.RecentPostsAdapter
import com.example.newsapp.adapter.SliderAdapter
import com.example.newsapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sliderHandler = Handler()
    private lateinit var viewPager2: ViewPager2

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var recentPostsAdapter: RecentPostsAdapter
    private lateinit var scienceTechAdapter: OtherNewsAdapter
    private lateinit var sportsAdapter: OtherNewsAdapter
    private lateinit var mainMenuAdapter: MainMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        setUpRvMenuItem()
        setUpViewPager2()
        setUpRecyclerViewRecent()
        setUpRecyclerViewScienceTech()
        setUpRecyclerViewSports()
        callNews()
        actionIntent(binding.textView5)
        actionIntent(binding.textView3)
        actionIntent(binding.textView4)
        actionIntent(binding.textView8)

        return binding.root
    }

    private fun setUpRvMenuItem() = binding.rvMenuItem.apply {
        mainMenuAdapter = MainMenuAdapter()
        binding.rvMenuItem.adapter = mainMenuAdapter
        binding.rvMenuItem.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun actionIntent(textView: TextView) {
        textView.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerViewSports() = binding.rvSports.apply {
        sportsAdapter = OtherNewsAdapter(context, "Sports")
        binding.rvSports.adapter = sportsAdapter
        binding.rvSports.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpRecyclerViewScienceTech() = binding.rvScienceTech.apply {
        scienceTechAdapter = OtherNewsAdapter(context, "Science & Tech")
        binding.rvScienceTech.adapter = scienceTechAdapter
        binding.rvScienceTech.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpRecyclerViewRecent() = binding.rvRecentPosts.apply {
        recentPostsAdapter = RecentPostsAdapter(context)
        binding.rvRecentPosts.adapter = recentPostsAdapter
        binding.rvRecentPosts.layoutManager = GridLayoutManager(context, 2)
    }

    fun setUpViewPager2() = binding.viewPagerImageSlider.apply {
        sliderAdapter = SliderAdapter()
        viewPager2 = binding.viewPagerImageSlider
        viewPager2.adapter = sliderAdapter
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(20))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.1f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 5000)
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }
    

    fun callNews() {
        try {
            CoroutineScope(Dispatchers.IO).launch {

                withContext(Dispatchers.Main) {
                    binding.progressBar.isVisible = true
                    binding.constraintLayout.isVisible = false
                }

                val response = async {
                    NewsClient.news.getNews(
                        "us",
                        "20",
                        "b574a5aaa3bf44ca9b5320f6447a107a"
                    )
                }.await()

                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        recentPostsAdapter.list = response.body()!!.articles
                    }
                }

                val responseBusiness = async {
                    NewsClient.news.getCategoryNews(
                        "us",
                        "business",
                        "50",
                        "b574a5aaa3bf44ca9b5320f6447a107a"
                    )

                }.await()

                if (responseBusiness.isSuccessful && responseBusiness.body() != null) {
                    withContext(Dispatchers.Main) {
                        sliderAdapter.list = responseBusiness.body()!!.articles
                    }
                }

                val responseScienceTech = async {
                    NewsClient.news.getCategoryNews(
                        "us",
                        "science",
                        "20",
                        "b574a5aaa3bf44ca9b5320f6447a107a"
                    )
                }.await()
                if (responseScienceTech.isSuccessful && responseScienceTech.body() != null) {
                    withContext(Dispatchers.Main) {
                        scienceTechAdapter.list = responseScienceTech.body()!!.articles
                    }
                }

                val responseSports = async {
                    NewsClient.news.getCategoryNews(
                        "us",
                        "sports",
                        "20",
                        "b574a5aaa3bf44ca9b5320f6447a107a"
                    )
                }.await()
                if (responseScienceTech.isSuccessful && responseScienceTech.body() != null) {
                    withContext(Dispatchers.Main) {
                        sportsAdapter.list = responseSports.body()!!.articles
                    }
                }
                withContext(Dispatchers.Main) {
                    binding.progressBar.isVisible = false
                    binding.constraintLayout.isVisible = true
                }
            }
        } catch (e: IOException) {
            Log.e("BusinessFragment", "$e")
        } catch (e: HttpException) {
            Log.e("BusinessFragment", "$e")
        }
    }

//    override fun onPause() {
//        super.onPause()
//        sliderHandler.postDelayed(sliderRunnable, 5000)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        sliderHandler.postDelayed(sliderRunnable, 5000)
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}