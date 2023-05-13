package com.barkdev.network.gateway

import retrofit2.Call

interface CallResult<out T> {
    fun <T> enqueue(
        call: Call<T>,
        apiListener: ApiListener<T>
    )
    fun <T> execute(call: Call<T>): T?
}