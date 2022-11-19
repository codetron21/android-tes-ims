package com.codetron.imscodingtest.shirtstore.data

sealed class StoreState<out T> {
    data class Success<T>(val data: T) : StoreState<T>()
    data class Error(val data: Throwable) : StoreState<Nothing>()
    object Loading : StoreState<Nothing>()
}
