package dev.bicutoru.aoe.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bicutoru.aoe.data.repository.AuthRepositoryImpl
import dev.bicutoru.aoe.domain.model.UserInfos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Empty)
    val authState: StateFlow<AuthState> = _authState

    fun login() {
        viewModelScope.launch {
            try {
                val response: UserInfos = repository.login()
                _authState.value = AuthState.Idle(response)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Erro! Tente novamente mais tarde.")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Empty
    }
}