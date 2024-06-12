package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.walkdog.Dogs
import com.example.walkdog.R

class MyAdapter(private var itemList: MutableList<Dogs>) ://, private val onItemClick: (Dogs) -> Unit) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.dog_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTextView.text = itemList[position].name
//        holder.itemView.setOnClickListener { onItemClick(itemList[position]) }

    }

    override fun getItemCount() = itemList.size

    fun updateData(newDogs: MutableList<Dogs>) {
        itemList = newDogs
        notifyDataSetChanged()
    }
}
