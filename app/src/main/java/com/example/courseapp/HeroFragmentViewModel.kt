package com.example.courseapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.Models.Ability
import com.example.courseapp.Models.Hero
import com.example.courseapp.Repository.FireBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HeroFragmentViewModel: ViewModel() {
    private var db :  FireBaseRepository = FireBaseRepository().getInstance()

    private var heroes: List<Hero> = emptyList()

    private var abilities: List<Ability> = emptyList()

    private val _heroesStrength = MutableStateFlow<List<Hero>>(emptyList())
    val heroesStrength = _heroesStrength.asStateFlow()

    private val _heroesAgility = MutableStateFlow<List<Hero>>(emptyList())
    val heroesAgility = _heroesAgility

    private val _heroesIntelligence = MutableStateFlow<List<Hero>>(emptyList())
    val heroesIntelligence = _heroesIntelligence.asStateFlow()

    private val _heroesUniversal = MutableStateFlow<List<Hero>>(emptyList())
    val heroesUniversal = _heroesUniversal.asStateFlow()

    private val _heroesSearched = MutableStateFlow<List<Hero>>(emptyList())
    val heroesSearched = _heroesSearched.asStateFlow()

    private val _loadHeroesStateFlow = MutableStateFlow(SearchMode.NONE)
    val loadHeroesStateFlow = _loadHeroesStateFlow.asStateFlow()



    fun loadHeroes() {
        viewModelScope.launch {
            _loadHeroesStateFlow.emit(SearchMode.LOADING)
            try {
                heroes = db.getHeroes().sortedBy { it.localized_name }
                _heroesStrength.emit(heroes.filter { it.attr_primary == "strength" })
                _heroesAgility.emit(heroes.filter { it.attr_primary == "agility" })
                _heroesIntelligence.emit(heroes.filter { it.attr_primary == "intelligence" })
                _heroesUniversal.emit(heroes.filter { it.attr_primary == "universal" })
                _loadHeroesStateFlow.emit(SearchMode.LOADED)
            } catch (e:Exception){
                Log.d("ViewModelGetItemError", e.toString())
                _loadHeroesStateFlow.emit(SearchMode.ERROR)

            }
        }
    }


    fun loadAbilities() {
        viewModelScope.launch {
            abilities = db.getAbilities()
        }
    }

    fun getHeroAbilities(heroId: Int): List<Ability> {
        return abilities.filter { it.hero_id == heroId }.sortedBy { it.slot } //как написать not contain чтобы не включать те которые not_learnable
    }

//    .filterNot { it.behavior!!.contains("not_learnable") }
    //.filterNot { it.behavior!!.contains("not_learnable") && it.behavior!!.contains("hidden") && it.scepter_grants == false && it.shard_grants == false}
    fun searchHeroes(name: String){
        viewModelScope.launch {
            _heroesSearched.emit(heroes.filter { it.localized_name!!.contains(name, true) })
        }
    }

    fun getHeroById(id: Int): Hero?{
        return heroes
            .find { h-> h.id == id }
    }
}
