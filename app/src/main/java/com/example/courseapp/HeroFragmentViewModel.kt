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

    private var heroes: List<Hero> = emptyList()

    private val _heroesStrength = MutableStateFlow<List<Hero>>(emptyList())
    val heroesStrength = _heroesStrength.asStateFlow()

    private val _heroesAgility = MutableStateFlow<List<Hero>>(emptyList())
    val heroesAgility = _heroesAgility

    private val _heroIntelligence = MutableStateFlow<List<Hero>>(emptyList())
    val heroesIntelligence = _heroIntelligence.asStateFlow()

    fun loadHeroes() {
        viewModelScope.launch {
            heroes = db.getHeroes().sortedBy { it.localized_name }
            _heroesStrength.emit(heroes.filter { it.attr_primary == "strength" })
            _heroesAgility.emit(heroes.filter { it.attr_primary == "agility" })
            _heroIntelligence.emit(heroes.filter { it.attr_primary == "intelligence" })
        }
    }

    fun getHeroById(id: Int): Hero?{
        return heroes
            .find { h-> h.id == id }
    }
}
