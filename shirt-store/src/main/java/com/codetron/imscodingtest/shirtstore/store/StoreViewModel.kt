package com.codetron.imscodingtest.shirtstore.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetron.imscodingtest.shirtstore.data.Category
import com.codetron.imscodingtest.shirtstore.data.DataSources
import com.codetron.imscodingtest.shirtstore.data.Product
import com.codetron.imscodingtest.shirtstore.data.StoreState
import com.codetron.imscodingtest.shirtstore.util.getData
import kotlinx.coroutines.launch

class StoreViewModel(
    private val dataSources: DataSources
) : ViewModel() {

    private val _stateCategories = MutableLiveData<StoreState<List<Category>>>()
    private val _stateProducts = MutableLiveData<StoreState<List<Product>>>()

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
    }

    fun selectCategory(id: Int) {
        val data = _stateCategories.value?.getData()?.map {
            it.apply { it.isSelected = it.id == id }
        } ?: emptyList()

        _stateCategories.value = StoreState.Success(data)
    }

    fun getProducts(categories: List<Category> = emptyList()) = viewModelScope.launch {
        _stateProducts.value = StoreState.Loading
        val category = categories.find { it.isSelected }
        if (category == null) {
            _stateProducts.value = StoreState.Error(Exception())
            return@launch
        }
        val result = dataSources.filterProductsByCategory(category.id)
        _stateProducts.value = StoreState.Success(result)
    }

    fun getStateCategories(): LiveData<StoreState<List<Category>>> = _stateCategories

    fun getStateProducts(): LiveData<StoreState<List<Product>>> = _stateProducts

    companion object {
        private const val TAG = "StoreViewModel"
    }

}