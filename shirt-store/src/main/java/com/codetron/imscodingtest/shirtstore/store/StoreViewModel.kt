package com.codetron.imscodingtest.shirtstore.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetron.imscodingtest.shirtstore.data.Category
import com.codetron.imscodingtest.shirtstore.data.DataSources
import com.codetron.imscodingtest.shirtstore.data.StoreState
import kotlinx.coroutines.launch

class StoreViewModel(
    private val dataSources: DataSources
) : ViewModel() {

    private val _stateCategories = MutableLiveData<StoreState<List<Category>>>()

    fun getCategories() = viewModelScope.launch {
        _stateCategories.value = StoreState.Loading
        val result = dataSources.getAllCategories()
        if (result.isEmpty()) {
            _stateCategories.value = StoreState.Error(Exception())
        } else {
            val allCategory = Category(
                id = 0,
                name = "Semua",
                colorHex = "#000000",
                isSelected = true
            )

            val newResult = mutableListOf<Category>()
            newResult.add(allCategory)
            newResult.addAll(result.toTypedArray())

            _stateCategories.value = StoreState.Success(newResult)
        }
        Log.d(TAG, result.toString())
    }

    fun selectCategory(id: Int) {
        val data = getDataCategories().map {
            it.apply { it.isSelected = it.id == id }
        }
        _stateCategories.value = StoreState.Success(data)
    }

    fun getStateCategories(): LiveData<StoreState<List<Category>>> = _stateCategories

    fun getDataCategories() = (_stateCategories.value as? StoreState.Success)?.data ?: emptyList()

    companion object {
        private const val TAG = "StoreViewModel"
    }

}