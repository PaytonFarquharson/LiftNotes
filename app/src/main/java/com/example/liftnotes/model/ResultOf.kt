package com.example.liftnotes.model

sealed class ResultOf<out T> {
    data class Success<out T>(val data: T): ResultOf<T>()
    data class Error(val message: String? = null): ResultOf<Nothing>()
}