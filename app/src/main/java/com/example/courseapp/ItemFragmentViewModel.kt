package com.example.courseapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.Models.Item
import com.example.courseapp.Repository.FireBaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException

class ItemFragmentViewModel: ViewModel() {
    private val db : FireBaseRepository = FireBaseRepository().getInstance()

    private var items: List<Item> = emptyList()

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    var itemList = _items.asStateFlow()

    private val _loadItemsStateFlow = MutableStateFlow(SearchMode.NONE)
    val loadItemsStateFlow = _loadItemsStateFlow.asStateFlow()

    fun loadItems(){
        viewModelScope.launch{
            _loadItemsStateFlow.emit(SearchMode.LOADING)
            try{
                items = db.getItems()
                _items.emit(items)
                _loadItemsStateFlow.emit(SearchMode.LOADED)

            } catch (e: Exception){
                Log.d("ViewModelGetItemError", e.toString())
                _loadItemsStateFlow.emit(SearchMode.ERROR)
            }
        }
    }

    fun getItemById(id: Int): Item?{
        return items
            .find { i-> i.id == id }
    }
}