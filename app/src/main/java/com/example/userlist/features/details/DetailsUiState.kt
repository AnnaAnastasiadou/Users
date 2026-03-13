package com.example.userlist.features.details

import com.example.userlist.features.User

data class DetailsUiState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val error: String? = null
)
