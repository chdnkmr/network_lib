package com.barkdev.common.data.usecases

import com.barkdev.network.sealed.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataUseCases @Inject constructor(private val userPostRepo: TestRepository) {

    fun getData(): Flow<ResultData.Success<UserPosts>> {
        return flow {
            userPostRepo.getTestData()?.let {
                emit(ResultData.Success(it))
            }
        }
    }
}