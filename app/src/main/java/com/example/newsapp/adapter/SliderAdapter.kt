package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.SliderItem
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemSliderBinding
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class SliderAdapter : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(val binding: ItemSliderBinding) : RecyclerView.ViewHolder(binding.root)


        private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.source == newItem.source

            }

        }

        private val differ = AsyncListDiffer<Article>(this, diffUtil)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
            return SliderViewHolder(ItemSliderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

        var list: List<Article>
            get() {
                return differ.currentList
            }
            set(value) {
                differ.submitList(value)
            }

        override fun onBindViewHolder(holder: SliderViewHolder,position: Int) {
            holder.binding.progressBar.isVisible = true
            holder.binding.constraintLayout.isVisible = false
            val article = list[position]
            val img = article.urlToImage
            if (img != null) {
                Picasso.get().load(img).into(holder.binding.itemSlider);
            }
            val title = article.title
            if (title != null) {
                holder.binding.tvTitle.text = title
            } else {
                holder.binding.tvTitle.text = ""
            }
//            holder.binding.progressBar.isVisible = false
            holder.binding.constraintLayout.isVisible = true
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }