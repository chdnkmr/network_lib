package com.barkdev.common.data.usecases

import com.barkdev.network.gateway.RetrofitInstance
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@Module
class TestRepository @Inject constructor() {

    private var retrofitInstance = RetrofitInstance.Builder().build(TestApiInterface::class.java)

    fun getTestData(): UserPosts? {
        return retrofitInstance.execute(retrofitInstance.service.getTestData())
    }
}