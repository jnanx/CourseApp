package com.example.courseapp.Adapter

import ImageLoadTask
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Models.Player
import com.example.courseapp.Models.Talent
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
        holder.level.text = currentItem.level.toString()
        holder.personKills.text = currentItem.kills.toString()
        holder.personDeaths.text = currentItem.deaths.toString()
        holder.personAssists.text = currentItem.assists.toString()

        currentItem.hero?.let{Glide.with(holder.itemView).load(BASE_URL + it.image).into(holder.heroIcon)}

        currentItem.item_0?.let {Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.item0Icon)}
        currentItem.item_1?.let {Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.item1Icon) }
        currentItem.item_2?.let {Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.item2Icon) }
        currentItem.item_3?.let {Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.item3Icon) }
        currentItem.item_4?.let {Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.item4Icon) }
        currentItem.item_5?.let {Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.item5Icon) }

        holder.netWorth.text = currentItem.net_worth.toString()

        currentItem.backpack_0?.let { Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.backpack1) }
        currentItem.backpack_1?.let { Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.backpack2) }
        currentItem.backpack_2?.let { Glide.with(holder.itemView).load(BASE_URL + it.icon).into(holder.backpack3) }
    //ImageLoadTask(holder.heroIcon).execute("https://dotabase.dillerm.io/dota-vpk${currentItem.hero?.heroIcon}")

        holder.abilityUpgrades.removeAllViews()
        currentItem.ability_upgrades?.forEach { ability ->
            val abilityImageView = ImageView(holder.itemView.context)
            val talent = currentItem.talent_upgrades?.firstOrNull{ it.ability_id == ability.id }
            if( talent != null ){
                if((talent.slot!! + 1) % 2 == 0) abilityImageView.setImageResource(R.drawable.left_tree)
                else abilityImageView.setImageResource(R.drawable.right_tree)
            }
            else Glide.with(holder.itemView).load(BASE_URL + ability.icon).into(abilityImageView)

            val params = LinearLayoutCompat.LayoutParams(100, 100)
            params.setMargins(6, 18, 0, 0)
            abilityImageView.layoutParams = params

            holder.abilityUpgrades.addView(abilityImageView)
        }

        var talentTreeSlots: Int = 0

        currentItem.talent_upgrades?.forEach {
            talentTreeSlots += it.slot!!
        }

    }

    override fun getItemCount(): Int {
        playersList = listStateFlow.value.map { it }
        return playersList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val heroName : TextView = itemView.findViewById(R.id.heroName)
        val personName : TextView = itemView.findViewById(R.id.personName)
        val heroIcon: ImageView = itemView.findViewById(R.id.heroIcon)
        val level : TextView = itemView.findViewById(R.id.levelText)
        val personKills: TextView = itemView.findViewById(R.id.personKills)
        val personDeaths: TextView = itemView.findViewById(R.id.personDeaths)
        val personAssists: TextView = itemView.findViewById(R.id.personAssists)

        val item0Icon : ImageView = itemView.findViewById(R.id.item1Icon)
        val item1Icon : ImageView = itemView.findViewById(R.id.item2Icon)
        val item2Icon : ImageView = itemView.findViewById(R.id.item3Icon)
        val item3Icon : ImageView = itemView.findViewById(R.id.item4Icon)
        val item4Icon : ImageView = itemView.findViewById(R.id.item5Icon)
        val item5Icon : ImageView = itemView.findViewById(R.id.item6Icon)

        val netWorth : TextView = itemView.findViewById(R.id.personNetWorth)

        val backpack1 : ImageView = itemView.findViewById(R.id.backpack1Icon)
        val backpack2 : ImageView = itemView.findViewById(R.id.backpack2Icon)
        val backpack3 : ImageView = itemView.findViewById(R.id.backpack3Icon)

        val abilityUpgrades: LinearLayoutCompat = itemView.findViewById(R.id.ability_upgrades)

    }


    companion object{
        const val BASE_URL = "https://dotabase.dillerm.io/dota-vpk"
    }
}