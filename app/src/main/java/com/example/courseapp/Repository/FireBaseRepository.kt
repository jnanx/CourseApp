package com.example.courseapp.Repository

import com.example.courseapp.Models.Ability
import com.example.courseapp.Models.Hero
import com.example.courseapp.Models.Item
import com.example.courseapp.Models.Talent
import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await

class FireBaseRepository {


    @Volatile private var INSTANCE : FireBaseRepository?= null

    fun getInstance() : FireBaseRepository{
        return  INSTANCE ?: synchronized(this){
            val instance = FireBaseRepository()
            INSTANCE = instance
            instance
        }
    }

    suspend fun getHeroes(): List<Hero>{
        val heroesList: MutableList<Hero> = mutableListOf()
        val task = FirebaseDatabase.getInstance().getReference("heroes").get()
        task.await()

        if (task.isSuccessful){
            if (task.result.exists()){
                for(hero in task.result.children){
                    heroesList.add(hero?.getValue(Hero::class.java)!!)
                }
            }
        }


        return  heroesList
    }

    suspend fun getItems(): List<Item>{
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


//        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("items")
//        reference.get().addOnSuccessListener {
//            if (it.exists()){
//                for( item in it.children){
//                    itemsList.add(item?.getValue(Item::class.java)!!)
//                }
//            }
//        }.addOnFailureListener {
//
//        }

       return itemsList
    }


    fun getAbilities() : List<Ability>{
        val abilitiesList : MutableList<Ability> = mutableListOf()
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("abilities")
        reference.get().addOnSuccessListener {
            if(it.exists()){
                for (ability in it.children){
                    abilitiesList.add(ability?.getValue(Ability::class.java)!!)
                }
            }
        }.addOnFailureListener {

        }

        return abilitiesList
    }

    fun getTalents() : List<Talent>{
        val talentsList : MutableList<Talent> = mutableListOf()
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("talents")
        reference.get().addOnSuccessListener {
            if(it.exists()){
                for (talent in it.children){
                    talentsList.add(talent?.getValue(Talent::class.java)!!)
                }
            }
        }.addOnFailureListener {

        }
        return talentsList
    }

}


