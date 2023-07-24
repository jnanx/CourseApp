package com.example.courseapp.Repository

import com.example.courseapp.Models.Ability
import com.example.courseapp.Models.Hero
import com.example.courseapp.Models.Item
import com.example.courseapp.Models.Talent
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class FireBaseRepository {


    @Volatile private var INSTANCE : FireBaseRepository?= null

    private var heroes: List<Hero>? = null
    private var items: List<Item>? = null
    private var abilities: List<Ability>? = null
    private var talents: List<Talent>? = null

    fun getInstance() : FireBaseRepository{
        return  INSTANCE ?: synchronized(this){
            val instance = FireBaseRepository()
            INSTANCE = instance
            instance
        }
    }

    suspend fun getHeroes(): List<Hero> {
        return heroes ?: let {
            val heroesList: MutableList<Hero> = mutableListOf()
            val task = FirebaseDatabase.getInstance().getReference("heroes").get()
            task.await()

            if (task.isSuccessful) {
                if (task.result.exists()) {
                    for (hero in task.result.children) {
                        heroesList.add(hero?.getValue(Hero::class.java)!!)
                    }
                }
            }
            heroes = heroesList
            heroes!!
        }
    }

    suspend fun getItems(): List<Item>{
        return items?: let {
            val itemsList: MutableList<Item> = mutableListOf()
            val task = FirebaseDatabase.getInstance().getReference("items").get()
            task.await()

            if(task.isSuccessful){
                if(task.result.exists()){
                    for(item in task.result.children){
                        itemsList.add(item?.getValue(Item::class.java)!!)
                    }
                }
            }
            items = itemsList
            items!!
        }

    }

    suspend fun getAbilities() : List<Ability> {
        return abilities?:let {
            val abilitiesList : MutableList<Ability> = mutableListOf()
            val task = FirebaseDatabase.getInstance().getReference("abilities").get()
            task.await()
            if(task.isSuccessful) {
                if (task.result.exists()) {
                    for (ability in task.result.children) {
                        abilitiesList.add(ability?.getValue(Ability::class.java)!!)
                    }
                }
            }
            abilities = abilitiesList
            abilities!!
        }

    }

    suspend fun getTalents() : List<Talent>{
        return talents?: let {
            val talentsList : MutableList<Talent> = mutableListOf()
            val task = FirebaseDatabase.getInstance().getReference("talents").get()
            task.await()
            if (task.isSuccessful){
                if(task.result.exists()){
                    for (talent in task.result.children){
                        talentsList.add(talent?.getValue(Talent::class.java)!!)
                    }
                }
            }
            talents=talentsList
            talents!!
        }

    }

}


