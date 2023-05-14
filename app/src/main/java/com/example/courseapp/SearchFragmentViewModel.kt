package com.example.courseapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.Models.MatchData
import com.example.courseapp.Models.Player
import com.example.courseapp.Retrofit.DotaBaseMatchDataRepository
import com.example.courseapp.Retrofit.MatchDataRepository
import com.example.courseapp.Room.MatchDataDatabase
import com.example.courseapp.Room.RoomDecoratorMatchDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchFragmentViewModel: ViewModel() {

    private lateinit var repository: MatchDataRepository

    private var _matchDataStateFlow = MutableStateFlow<MatchData?>(null)

    var matchDataStateFlow = _matchDataStateFlow.asStateFlow()

    private var _radiantPlayersStateFlow = MutableStateFlow<List<Player>>(emptyList())

    var radiantPlayersStateFlow = _radiantPlayersStateFlow.asStateFlow()

    private var _direPlayersStateFlow = MutableStateFlow<List<Player>>(emptyList())

    var direPlayersStateFlow = _direPlayersStateFlow.asStateFlow()

    private val _searchModeStateFlow = MutableStateFlow(SearchMode.NONE)

    val searchModeStateFlow = _searchModeStateFlow.asStateFlow()


    fun getMatchData(matchId: Long){
        viewModelScope.launch {
            _searchModeStateFlow.emit(SearchMode.LOADING)
            try {
                val matchData = repository.getMatchData(matchId)
                _matchDataStateFlow.emit(matchData)
                val (radiant, dire) = matchData.players.partition { it.isRadiant == true }
                _direPlayersStateFlow.emit(dire)
                _radiantPlayersStateFlow.emit(radiant)
                _searchModeStateFlow.emit(SearchMode.LOADED)
            } catch (e: Exception) {
                Log.d("ViewModelGetMatchDataError", e.toString())
                _searchModeStateFlow.emit(SearchMode.ERROR)
            }
        }
    }

    fun setDb(db: MatchDataDatabase) {
        repository = RoomDecoratorMatchDataRepository(DotaBaseMatchDataRepository(), db)
    }
}