package com.example.courseapp.Repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.courseapp.Models.Hero
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class HeroRepository {

    @Volatile private var INSTANCE : HeroRepository?= null

    fun getInstance() : HeroRepository{
        return  INSTANCE ?: synchronized(this){
            val instance = HeroRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadHeroes(heroesList : MutableLiveData<List<Hero>>){
        val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("heroes")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try{
                    val _heroesList : List<Hero> = snapshot.children.map {
                        dataSnapshot ->
                        dataSnapshot.getValue(Hero::class.java)!!
                    }

                    heroesList.postValue(_heroesList)

                }catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getHeroes(): List<Hero>{
        val heroesList: MutableList<Hero> = mutableListOf()
        val reference : DatabaseReference = FirebaseDatabase.getInstance().getReference("heroes")
        reference.get().addOnSuccessListener {
            if (it.exists()){
                for(hero in it.children){
                    heroesList.add(hero?.getValue(Hero::class.java)!!)
                }
            }
        }.addOnFailureListener{

        }

        return  heroesList
    }


}


