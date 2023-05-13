package com.barkdev.network.sealed

sealed class ResultData<out T> {
    data class Loading<T>(val nothing: T? = null) : ResultData<T>()
    data class Success<out T>(val data: T? = null) : ResultData<T>()
    data class Failure<T>(val message: T? = null) : ResultData<T>()
}
