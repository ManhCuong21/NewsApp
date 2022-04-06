package com.example.newsapp

import android.telecom.Call
import com.example.newsapp.Model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String
    ): Response<News>

    @GET("top-headlines")
    suspend fun getCategoryNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String
    ): Response<News>

    @GET("top-headlines?sources=bbc-news&apiKey=b574a5aaa3bf44ca9b5320f6447a107a")
    suspend fun getBBCNews() : Response<News>
}