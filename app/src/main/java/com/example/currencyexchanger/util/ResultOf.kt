package com.example.currencyexchanger.util

sealed class ResultOf<out T> {
    data object Loading : ResultOf<Nothing>()
    data class Success<T>(val data: T) : ResultOf<T>()
    data class Error(val exception: Exception) : ResultOf<Nothing>()
}
