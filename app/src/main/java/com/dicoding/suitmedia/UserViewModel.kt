package com.dicoding.suitmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.suitmedia.network.ApiService
import com.dicoding.suitmedia.network.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val apiService = ApiService.create()
    private var currentPage = 1
    var isLoading = false

    fun loadUsers(page: Int = currentPage, perPage: Int = 10) {
        if (isLoading) return
        isLoading = true

        apiService.getUsers(page, perPage).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val currentUsers = _users.value ?: emptyList()
                    val newUsers = response.body()?.data ?: emptyList()
                    _users.value = currentUsers + newUsers
                    currentPage++
                }
                isLoading = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading = false
            }
        })
    }

    fun refreshData() {
        currentPage = 1
        _users.value = emptyList()
        loadUsers()
    }
}
