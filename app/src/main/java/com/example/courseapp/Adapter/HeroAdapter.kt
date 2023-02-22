package com.example.courseapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Models.Hero
import com.example.courseapp.R
import kotlinx.coroutines.flow.StateFlow

class HeroAdapter(private val listStateFlow: StateFlow<List<Hero>>) : RecyclerView.Adapter<HeroAdapter.MyViewHolder>() {

    private var heroList: List<Hero> = emptyList()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val heroIcon: ImageView = itemView.findViewById(R.id.heroIconBox)
        val heroName: TextView = itemView.findViewById(R.id.heroNameBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.hero_item,
            parent, false
        )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = heroList[position]

        currentItem.image?.let { Glide.with(holder.itemView).load(BASE_URL + it).into(holder.heroIcon) }
        holder.heroName.text = currentItem.localized_name

    }

    override fun getItemCount(): Int {
        heroList = listStateFlow.value.map { it }
        return heroList.size
    }

    companion object{
        const val BASE_URL = "https://dotabase.dillerm.io/dota-vpk/"
    }
}
