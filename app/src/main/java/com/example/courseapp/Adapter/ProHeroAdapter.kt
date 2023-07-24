package com.example.courseapp.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Models.ProMatchData
import com.example.courseapp.R
import kotlinx.coroutines.flow.StateFlow

class ProHeroAdapter(private val matches: StateFlow<List<ProMatchData>>) : RecyclerView.Adapter<ProHeroAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val proHeroIcon : ImageView = itemView.findViewById(R.id.heroIconImgBox)
        val proPlayerName : TextView = itemView.findViewById(R.id.proPlayerName)
        val proLeagueName : TextView = itemView.findViewById(R.id.proLeagueName)
        val proPlayerKills : TextView = itemView.findViewById(R.id.proPersonKills)
        val proPlayerDeaths : TextView = itemView.findViewById(R.id.proPersonDeaths)
        val proPlayerAssists : TextView = itemView.findViewById(R.id.proPersonAssists)

        var proMatchIsWon : CardView = itemView.findViewById(R.id.isWinCard)
    }

    var list: List<ProMatchData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.pro_hero_item,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        list = matches.value
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]

        currentItem.heroIconURL.let { Glide.with(holder.proHeroIcon).load(it).into(holder.proHeroIcon) }
        holder.proPlayerName.text = currentItem.match_id.toString()
        holder.proLeagueName.text = currentItem.league_name
        holder.proPlayerKills.text = currentItem.kills.toString()
        holder.proPlayerDeaths.text = currentItem.deaths.toString()
        holder.proPlayerAssists.text = currentItem.assists.toString()


        val isWin = if (currentItem.radiant) currentItem.radiant_win
        else !currentItem.radiant_win


        if(isWin)
            holder.proMatchIsWon.setCardBackgroundColor(Color.parseColor("#4D8B31"))
        else holder.proMatchIsWon.setCardBackgroundColor(Color.parseColor("#8C3131"))

    }

    companion object{
        const val BASE_URL = "https://dotabase.dillerm.io/dota-vpk"
    }

}