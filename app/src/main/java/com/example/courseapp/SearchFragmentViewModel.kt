package com.example.courseapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.Models.MatchData
import com.example.courseapp.Models.Player
import com.example.courseapp.Retrofit.DotaBaseMatchDataRepository
import com.example.courseapp.Retrofit.MatchDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchFragmentViewModel: ViewModel() {

    //TODO: DI
    private val repository: MatchDataRepository = DotaBaseMatchDataRepository()

    private var _matchDataStateFlow = MutableStateFlow<MatchData?>(null)

    var matchDataStateFlow = _matchDataStateFlow.asStateFlow()

    private var _playersStateFlow = MutableStateFlow(listOf<Player>())

    var playersStateFlow = _playersStateFlow.asStateFlow()

    private val _searchModeStateFlow = MutableStateFlow(SearchMode.NONE)

    val searchModeStateFlow = _searchModeStateFlow.asStateFlow()


    fun getMatchData(matchId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            _searchModeStateFlow.emit(SearchMode.LOADING)
            try {
                val matchData = repository.getMatchData(matchId)
                _matchDataStateFlow.emit(matchData)
                _playersStateFlow.emit(matchData.players)
                _searchModeStateFlow.emit(SearchMode.LOADED)
            } catch (e: Exception) {
                Log.d("ViewModelGetMatchDataError", e.toString())
                _searchModeStateFlow.emit(SearchMode.ERROR)
            }
        }
    }
}