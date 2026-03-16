package com.example.userlist.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.mapper.toUser
import com.example.userlist.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProfileDetails()
    }

    private fun loadProfileDetails() {
        viewModelScope.launch {
            usersRepository.observeUserById(1).collect{userDto ->
                if (userDto != null) {
                    _uiState.update { it.copy(isLoading = false, data = userDto.toUser(), error = null) }
                } else {
                    _uiState.update {
                        it.copy(isLoading = true, data = null, error = null)
                    }
                }
            }
        }
//        _uiState.update {
//            it.copy(isLoading = true, data = null, error = null)
//        }
//        val userDto = usersRepository.getUserById(1)
//        if (userDto != null) {
//            _uiState.update {
//                it.copy(isLoading = false, data = userDto.toUser(), error = null)
//            }
//        } else {
//            _uiState.update { it.copy(isLoading = false, data = null, error = null) }
//        }
    }
}