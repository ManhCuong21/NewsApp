package com.example.newsapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Activity.WebActivity
import com.example.newsapp.Model.Article
import com.example.newsapp.databinding.ItemRecentPostsBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class RecentPostsAdapter(val context: Context) : RecyclerView.Adapter<RecentPostsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRecentPostsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source == newItem.source
        }

    }

    private val differ = AsyncListDiffer<Article>(this, diffUtil)

    var list: List<Article>
        get() {
            return differ.currentList
        }
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecentPostsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.progressBar.isVisible = true

        val article = list[position]
        val img = article.urlToImage
        if (img != null) {
            Picasso.get().load(img).into(holder.binding.imageView);
        }
        val title = article.title
        if (title != null) {
            holder.binding.tvTitle.text = title
        }
        val author = article.source.name
        if (author != null) {
            holder.binding.tvAuthor.text = author
        } else {
            holder.binding.tvAuthor.text = ""
        }
        holder.binding.itemNews.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", article.url)
            context.startActivity(intent)
        }
        holder.binding.progressBar.isVisible = false
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(publishedAt: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val day = simpleDateFormat.format(publishedAt)
        val simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat2.format(day)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}