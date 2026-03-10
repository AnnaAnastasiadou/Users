package com.example.userlist.data.repository

import com.example.userlist.data.remote.NetworkResult
import com.example.userlist.data.remote.UserDto

interface UsersRepository {
    suspend fun getAllUsers(): NetworkResult<List<UserDto>>
    suspend fun getUserById(id: Int): NetworkResult<UserDto>
}