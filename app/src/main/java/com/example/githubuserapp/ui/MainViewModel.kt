package com.example.githubuserapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.response.UserDetail
import com.example.githubuserapp.data.response.UserItem
import com.example.githubuserapp.data.response.RelatedUser
import com.example.githubuserapp.data.response.UsersResponse
import com.example.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val apiService = ApiConfig.getApiService()
    fun searchUser(username: String) {
        _isLoading.value = true
        val client = apiService.searchUsers(username)
        client.enqueue(object: Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>,
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listUser.value = responseBody.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<UsersResponse>,
                t: Throwable,
            ) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = apiService.getDetailUser(username)

        client.enqueue(object: Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _user.value = responseBody
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<UserDetail>,
                t: Throwable,
            ) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowers(username: String) {
        _isLoading.value = true
        val client = apiService.getFollowers(username)

        client.enqueue(object: Callback<List<RelatedUser>> {
            override fun onResponse(call: Call<List<RelatedUser>>, response: Response<List<RelatedUser>>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listFollower.value = responseBody as List<RelatedUser>
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<List<RelatedUser>>,
                t: Throwable,
            ) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }fun getUserFollowings(username: String) {
        _isLoading.value = true
        val client = apiService.getFollowings(username)

        client.enqueue(object: Callback<List<RelatedUser>> {
            override fun onResponse(call: Call<List<RelatedUser>>, response: Response<List<RelatedUser>>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listFollowing.value = responseBody as List<RelatedUser>
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<List<RelatedUser>>,
                t: Throwable,
            ) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private val _user = MutableLiveData<UserDetail?>()
    val user: LiveData<UserDetail?> = _user

    private val _listUser = MutableLiveData<List<UserItem>>()
    val listUser: LiveData<List<UserItem>> = _listUser

    private val _listFollower = MutableLiveData<List<RelatedUser>>()
    val listFollower: LiveData<List<RelatedUser>> = _listFollower

    private val _listFollowing = MutableLiveData<List<RelatedUser>>()
    val listFollowing: LiveData<List<RelatedUser>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }
}