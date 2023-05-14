package com.example.courseapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Models.Ability
import com.example.courseapp.R
import okhttp3.internal.notify
import org.w3c.dom.Text

class AbilityAdapter(private val list: List<Ability>) :
    RecyclerView.Adapter<AbilityAdapter.MyViewHolder>() {


    data class AbilityCard(
        val ability: Ability,
        var isCollapsed: Boolean = true
    )

    private val abilityCardsList = list.map { AbilityCard(it) }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val abilityIcon: ImageView = v.findViewById(R.id.abilityImgBox)
        val abilityName: TextView = v.findViewById(R.id.abilityName)
        val abilityBehavior: TextView = v.findViewById(R.id.abilityBehavior)
        val abilityManaCost: TextView = v.findViewById(R.id.abilityManaCost)
        val abilityCooldown: TextView = v.findViewById(R.id.abilityCooldown)
        val abilityDamage: TextView = v.findViewById(R.id.abilityDamage)
        val abilityCastRange: TextView = v.findViewById(R.id.abilityCastRange)
        val abilityDescription: TextView = v.findViewById(R.id.abilityDescription)
        val abilityNote: TextView = v.findViewById(R.id.abilityNote)
        val abilityShard: TextView = v.findViewById(R.id.abilityShard)
        val abilityScepter: TextView = v.findViewById(R.id.abilityScepter)
        val shardLayout: LinearLayoutCompat = v.findViewById(R.id.shardDescLayout)
        val scepterLayout: LinearLayoutCompat = v.findViewById(R.id.scepterDescLayout)
        val isScepter: CardView = v.findViewById(R.id.isScepter)
        val isShard: CardView = v.findViewById(R.id.isShard)

        val behaviorLayout: LinearLayoutCompat = v.findViewById(R.id.behaviorLayout)
        val manaCostLayout: LinearLayoutCompat = v.findViewById(R.id.manaCostLayout)
        val cooldownLayout: LinearLayoutCompat = v.findViewById(R.id.cooldownLayout)
        val damageLayout: LinearLayoutCompat = v.findViewById(R.id.damageLayout)
        val castRangeLayout: LinearLayoutCompat = v.findViewById(R.id.castRangeLayout)

        val additionalAbilityInfo: LinearLayoutCompat = v.findViewById(R.id.additionalAbilityInfo)
        val abilityCardView: CardView = v.findViewById(R.id.abilityCardView)

        val abilitySlot: TextView = v.findViewById(R.id.abilitySlot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityAdapter.MyViewHolder {
        val abilityView = LayoutInflater.from(parent.context).inflate(
            R.layout.skill_item,
            parent, false
        )

        return MyViewHolder(abilityView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AbilityAdapter.MyViewHolder, position: Int) {
        val currentAbility = list[position]

        holder.additionalAbilityInfo.visibility =
            if (abilityCardsList[position].isCollapsed) View.GONE
            else View.VISIBLE

        holder.abilityCardView.setOnClickListener {
            abilityCardsList[position].isCollapsed = !abilityCardsList[position].isCollapsed
            notifyItemChanged(position)
        }

        currentAbility.icon?.let {
            Glide.with(holder.itemView).load(BASE_URL + it).into(holder.abilityIcon)
        }
        holder.abilityName.text = currentAbility.localized_name
        if (currentAbility.behavior.isNullOrBlank()) {
            holder.behaviorLayout.visibility = View.GONE
        } else holder.abilityBehavior.text = currentAbility.behavior

        if (currentAbility.mana_cost.isNullOrBlank()) {
            holder.manaCostLayout.visibility = View.GONE
        } else holder.abilityManaCost.text = currentAbility.mana_cost.replace(" ", "/")

        if (currentAbility.cooldown.isNullOrBlank()) {
            holder.cooldownLayout.visibility = View.GONE
        } else holder.abilityCooldown.text = currentAbility.cooldown.replace(" ", "/")

        if (currentAbility.ability_special?.find { it.header == "DAMAGE:" }?.value.isNullOrBlank()) {
            holder.damageLayout.visibility = View.GONE
        } else holder.abilityDamage.text =
            currentAbility.ability_special?.find { it.header == "DAMAGE:" }?.value!!.replace(
                " ",
                "/"
            )

        if (currentAbility.cast_range.isNullOrBlank())
            holder.castRangeLayout.visibility = View.GONE
        else
            holder.abilityCastRange.text = currentAbility.cast_range.replace(" ", "/")


        currentAbility.description?.let { holder.abilityDescription.text = it.replace("**", "") }

        if (currentAbility.note.isNullOrBlank()) {
            holder.abilityNote.visibility = View.GONE
        } else {
            holder.abilityNote.text = currentAbility.note.replace("**", "")
            holder.abilityNote.visibility = View.VISIBLE
        }



        if (currentAbility.scepter_upgrades == true || currentAbility.scepter_grants == true) {
            holder.isScepter.visibility = View.VISIBLE
            holder.scepterLayout.visibility = View.VISIBLE
            currentAbility.scepter_description?.let {
                holder.abilityScepter.text = it.replace("**", "")
            }
        } else {
            holder.isScepter.visibility = View.GONE
            holder.scepterLayout.visibility = View.GONE
        }


        if (currentAbility.shard_upgrades == true || currentAbility.shard_grants == true) {
            holder.isShard.visibility = View.VISIBLE
            holder.shardLayout.visibility = View.VISIBLE
            currentAbility.shard_description?.let {
                holder.abilityShard.text = it.replace("**", "")
            }
        } else {
            holder.isShard.visibility = View.GONE
            holder.shardLayout.visibility = View.GONE
        }

        holder.abilitySlot.text = currentAbility.slot.toString()

    }


    companion object {
        const val BASE_URL = "https://dotabase.dillerm.io/dota-vpk/"
    }

}