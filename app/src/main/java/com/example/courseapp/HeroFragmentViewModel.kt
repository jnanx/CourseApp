package com.example.courseapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.Models.Hero
import com.example.courseapp.Repository.FireBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HeroFragmentViewModel: ViewModel() {
    private var db :  FireBaseRepository = FireBaseRepository().getInstance()

    private var _heroesStateFlow = MutableStateFlow<List<Hero>>(emptyList())
    var heroesStateFlow = _heroesStateFlow.asStateFlow()


    fun loadHeroes(){
        viewModelScope.launch(Dispatchers.IO) {
            val heroList = db.getHeroes()
            _heroesStateFlow.emit(heroList)
        }
    }



    fun getHeroById(id: Int): Hero?{
        return _heroesStateFlow.value.find { h-> h.id == id }
    }
}
