package com.example.newsapp.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    @Expose
    val articles: List<Article>,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("totalResults")
    @Expose
    val totalResults: Int
)