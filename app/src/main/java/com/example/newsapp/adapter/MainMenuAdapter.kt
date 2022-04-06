package com.example.newsapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemMenuBinding
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.util.*


class MainMenuAdapter: RecyclerView.Adapter<MainMenuAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    private val list = listOf("Home","News","Tech","Science","General","Business","Health","Sports")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val string = item.substring(0,1)
        holder.binding.tvTitle.text = item
        holder.binding.tvText.text = string
        val r = Random()
        val color = Color.argb(255,r.nextInt(256),r.nextInt(256),r.nextInt(256))
        holder.binding.tvText.setBackgroundColor(color)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}