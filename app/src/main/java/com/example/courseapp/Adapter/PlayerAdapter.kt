package com.example.courseapp.Adapter

import ImageLoadTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Models.Player
import com.example.courseapp.R
import kotlinx.coroutines.flow.StateFlow

class PlayerAdapter(private val listStateFlow: StateFlow<List<Player>>) : RecyclerView.Adapter<PlayerAdapter.MyViewHolder>(){

    private var playersList: List<Player> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.player_item,
            parent, false

        )

        return  MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = playersList[position]

        holder.heroName.text = currentItem.hero?.localized_name
        holder.personName.text = currentItem.name
        holder.personKills.text = currentItem.kills.toString()
        holder.personDeaths.text = currentItem.deaths.toString()
        holder.personAssists.text = currentItem.assists.toString()

        Glide.with(holder.itemView).load("https://dotabase.dillerm.io/dota-vpk"+currentItem.hero?.image).into(holder.heroIcon)
        //ImageLoadTask(holder.heroIcon).execute("https://dotabase.dillerm.io/dota-vpk${currentItem.hero?.heroIcon}")
    }

    override fun getItemCount(): Int {
        playersList = listStateFlow.value.map { it }
        return playersList.size
    }

    /*fun updateHeroList(heroList: List<Player>){
        this.playersList.clear()
        this.playersList.addAll(heroList)
        notifyDataSetChanged()

    }*/

    class MyViewHolder(val itemView : View) : RecyclerView.ViewHolder(itemView){
        val heroName : TextView = itemView.findViewById(R.id.heroName)
        val personName : TextView = itemView.findViewById(R.id.personName)
        val heroIcon: ImageView = itemView.findViewById(R.id.heroIcon)
        val personKills: TextView = itemView.findViewById(R.id.personKills)
        val personDeaths: TextView = itemView.findViewById(R.id.personDeaths)
        val personAssists: TextView = itemView.findViewById(R.id.personAssists)
    }
}