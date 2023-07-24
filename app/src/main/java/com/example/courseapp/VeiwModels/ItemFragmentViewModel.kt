package com.example.courseapp.VeiwModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.Models.Item
import com.example.courseapp.Repository.FireBaseRepository
import com.example.courseapp.SearchMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemFragmentViewModel : ViewModel() {
    private val db: FireBaseRepository = FireBaseRepository().getInstance()

    private var items: List<Item> = emptyList()

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    var itemList = _items.asStateFlow()

    private val _loadItemsStateFlow = MutableStateFlow(SearchMode.NONE)
    val loadItemsStateFlow = _loadItemsStateFlow.asStateFlow()

    private val _itemsSearched = MutableStateFlow<List<Item>>(emptyList())
    val itemsSearched = _itemsSearched.asStateFlow()

    private val _itemsRecipe = MutableStateFlow<List<Item>>(emptyList())
    val itemsRecipe = _itemsRecipe.asStateFlow()

    private val _recipeVisibility = MutableStateFlow(true)
    var recipeVisibility = _recipeVisibility.asStateFlow()

    var currentItem: Item? = null
        private set


    fun loadItems() {
        viewModelScope.launch {
            _loadItemsStateFlow.emit(SearchMode.LOADING)
            try {
                items = db.getItems()
                    .filter { checkIsItemAvailable(it) }

                _items.emit(items.filterNot {
                    it.localized_name!!.contains(
                        "recipe",
                        true
                    )
                })
                _loadItemsStateFlow.emit(SearchMode.LOADED)
            } catch (e: Exception) {
                Log.d("ViewModelGetItemError", e.toString())
                _loadItemsStateFlow.emit(SearchMode.ERROR)
            }
        }
    }

    private fun checkIsItemAvailable(item: Item): Boolean {
        val isAvailableNeutral = item.json_data?.ItemIsNeutralDrop != null && item.neutral_tier != null
        return isAvailableNeutral || item.json_data?.ItemSellable == null
    }

    fun searchItems(name: String) {
        viewModelScope.launch {
            _itemsSearched.emit(items.filter { it.localized_name!!.contains(name, true) })
        }
    }

    private fun loadRecipe() {
        viewModelScope.launch{
            val itemRecipeNames = currentItem?.recipe?.split("|")
            if (itemRecipeNames != null) {
                val recipeItems = itemRecipeNames.map { recipeName -> items.find { item -> recipeName == item.name }!! }

                _itemsRecipe.emit(recipeItems)
                _recipeVisibility.emit(true)
            } else {
                _recipeVisibility.emit(false)
                _itemsRecipe.emit(emptyList())
            }
        }
    }


    fun setCurrentItem(id: Int) {
        currentItem = items
            .find { i -> i.id == id }
        loadRecipe()
    }
}