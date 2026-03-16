package com.example.userlist.data.repository

import com.example.userlist.data.remote.NetworkResult
import com.example.userlist.data.remote.UserDto
import com.example.userlist.features.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getAllUsers(): NetworkResult<List<UserDto>>
    fun getUserById(id: Int): UserDto?
    fun getCachedUsers(): List<UserDto>?

    fun getDummyUsers(): List<User>
    fun observeUserById(id: Int): Flow<UserDto?>
}