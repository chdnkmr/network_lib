package com.barkdev.network.gateway

interface ApiListener<T> {
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
}