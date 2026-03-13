package com.example.userlist.features.details

import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.mapper.toUser
import com.example.userlist.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userId = savedStateHandle.get<Int>("USER_ID") ?: -1
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        loadUserDetails()
    }

    private fun loadUserDetails() {
        _uiState.update {
            it.copy(isLoading = true, data = null, error = null)
        }
        val userDto = usersRepository.getUserById(userId)
        if (userDto != null) {
            _uiState.update {
                it.copy(isLoading = false, data = userDto.toUser(), error = null)
            }
        } else {
            _uiState.update { it.copy(isLoading = false, data = null, error = null) }
        }
    }

}