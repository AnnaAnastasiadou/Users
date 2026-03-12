package com.example.userlist.data.repository

import com.example.userlist.data.remote.NetworkResult
import com.example.userlist.data.remote.UserDto
import com.example.userlist.features.User

interface UsersRepository {
    suspend fun getAllUsers(): NetworkResult<List<UserDto>>
    suspend fun getUserById(id: Int): UserDto?
    fun getCachedUsers(): List<UserDto>?

    fun getDummyUsers(): List<User>
}