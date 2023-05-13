package com.barkdev.common.data.usecases

import retrofit2.Call
import retrofit2.http.GET


interface TestApiInterface {
    @GET("posts")
    fun getTestData(): Call<UserPosts>
}