package com.example.newsapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.News
import com.example.newsapp.NewsAPI
import com.example.newsapp.NewsClient
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentBusinessBinding
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class BusinessFragment : Fragment() {
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        setUpRecycleView()
        callNews()
        return binding.root
    }

    fun setUpRecycleView() = binding.rcvBusiness.apply {
        newsAdapter = NewsAdapter(context)
        binding.rcvBusiness.adapter = newsAdapter
        binding.rcvBusiness.layoutManager = LinearLayoutManager(context)
    }

    fun callNews(){
        try {
            binding.progressBar.isVisible = true
            binding.rcvBusiness.isVisible = false
            CoroutineScope(Dispatchers.IO).launch {
                val response = async {
                    NewsClient.news.getCategoryNews(
                        "us",
                        "business",
                        "20",
                        "b574a5aaa3bf44ca9b5320f6447a107a"
                    )
                }.await()

                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main){
                        newsAdapter.list = response.body()!!.articles
                        binding.progressBar.isVisible = false
                        binding.rcvBusiness.isVisible = true
                    }
//                    val list = response.body()!!.articles
//                            Log.d("AAAAAA", "${list.size}")
                }
            }
        } catch (e: IOException) {
            Log.e("BusinessFragment", "$e")
        } catch (e: HttpException) {
            Log.e("BusinessFragment", "$e")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}