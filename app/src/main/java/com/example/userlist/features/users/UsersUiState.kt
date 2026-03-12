package com.example.userlist.features.users

import com.example.userlist.features.User

data class UsersUiState(
    val isLoading: Boolean = false,
    val data: List<User>? = null,
    val error: String? =null
)
