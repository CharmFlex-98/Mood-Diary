package com.mooddiary.utils

sealed interface Async<out T> {
    data class Success<T>(val data: T): Async<T>
    data class Error(val error: String): Async<Nothing>
    object Loading: Async<Nothing>
}