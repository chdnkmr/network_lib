package com.barkdev.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.barkdev.common.data.usecases.UserDataUseCases
import com.barkdev.common.data.usecases.UserPosts
import com.barkdev.network.sealed.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataUseCases: UserDataUseCases
) : ViewModel() {

    private val userPostMutableLiveData =
        MutableLiveData<ResultData<UserPosts>>()

    val userPostLiveData: LiveData<ResultData<UserPosts>>
        get() = userPostMutableLiveData

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            userPostMutableLiveData.postValue(userDataUseCases.getData().asLiveData().value)
        }
    }
}