package com.example.userlist.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users")
    suspend fun getAllUsers() : Response<List<UserDto>>
    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ) : Response<UserDto>
}