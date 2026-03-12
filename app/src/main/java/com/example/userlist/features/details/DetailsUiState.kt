package com.example.userlist.features.details

import com.example.userlist.data.remote.UserDto

data class DetailsUiState(
    val isLoading: Boolean = false,
    val data: UserDto? = null
)
