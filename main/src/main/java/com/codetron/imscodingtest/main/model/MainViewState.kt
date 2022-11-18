package com.codetron.imscodingtest.main.model

sealed class MainViewState<out T> {
    data class Success<T>(val data: T) : MainViewState<T>()
    object Loading : MainViewState<Nothing>()
    data class Error(val data: Throwable) : MainViewState<Nothing>()
}