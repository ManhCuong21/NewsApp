package com.example.newsapp.adapter

import android.content.Context
import android.content.Intent
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Activity.WebActivity
import com.example.newsapp.Model.Article
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source == newItem.source

        }

    }

    private val differ = AsyncListDiffer<Article>(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var list: List<Article>
        get() {
            return differ.currentList
        }
        set(value) {
            differ.submitList(value)
        }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = list[position]
        val img = article.urlToImage
        if (img != null) {
            Picasso.get().load(img).into(holder.binding.imageView);
        }
        holder.binding.tvTitle.text = article.title
        val author = article.author
        if (author != null) {
            holder.binding.tvAuthor.text = "Author: $author"
        } else {
            holder.binding.tvAuthor.text = ""
        }
        val desc = article.description
        if (desc != null) {
            holder.binding.tvDesc.text = article.content
        } else {
            holder.binding.tvDesc.text = ""
        }
        val expandedView = holder.binding.expandableView
        holder.binding.btnArrow.setOnClickListener {
            if (expandedView.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.binding.cardView, AutoTransition())
                expandedView.visibility = View.VISIBLE
                holder.binding.btnArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            } else {
                TransitionManager.beginDelayedTransition(holder.binding.cardView, AutoTransition())
                expandedView.visibility = View.GONE
                holder.binding.btnArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
        holder.binding.itemNews.setOnClickListener {
            val intent = Intent (context, WebActivity::class.java)
            intent.putExtra("url",article.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}