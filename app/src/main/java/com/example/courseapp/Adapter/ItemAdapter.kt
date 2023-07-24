package com.example.courseapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Models.Item
import com.example.courseapp.R
import kotlinx.coroutines.flow.StateFlow

class ItemAdapter(private val list: StateFlow<List<Item>>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    fun interface OnItemClickListener{
        fun onItemClick(Id: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener):ItemAdapter{
        mListener = listener
        return this
    }

    private var itemList: List<Item> = emptyList()

    class ItemViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView){
        val itemIcon: ImageView = itemView.findViewById(R.id.itemImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_item,
            parent, false
        )

        return ItemViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        val currentItem = itemList[position]
        currentItem.icon?.let { Glide.with(holder.itemView).load(BASE_URL + it).into(holder.itemIcon) }
        holder.itemIcon.setOnClickListener {
            currentItem.id?.let { id -> mListener.onItemClick(id) }
        }
    }

    override fun getItemCount():Int{
        itemList = list.value
        return itemList.size
    }

    companion object{
        const val BASE_URL = "https://dotabase.dillerm.io/dota-vpk"
    }
}