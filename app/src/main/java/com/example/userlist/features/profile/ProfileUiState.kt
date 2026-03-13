package com.example.userlist.features.profile

import com.example.userlist.features.User

data class ProfileUiState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val error: String? = null
)

