package dev.bicutoru.aoe.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LoginUiState(
            email = savedStateHandle["email"] ?: "",
            password = savedStateHandle["password"] ?: ""
        )
    )
    val uiState: StateFlow<LoginUiState> = _uiState

    // Atualiza email e salva no SavedStateHandle
    fun onEmailChange(newEmail: String) {
        val trimmedEmail = newEmail.trim()
        _uiState.update { current ->
            val emailError = if (current.emailError != null &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()
            ) null else current.emailError

            current.copy(
                email = trimmedEmail,
                isButtonEnabled = trimmedEmail.isNotBlank() && current.password.isNotBlank(),
                emailError = emailError
            )
        }
        savedStateHandle["email"] = trimmedEmail
    }

    fun onPasswordChange(newPassword: String) {
        val trimmedPassword = newPassword.trim()
        val valid = trimmedPassword.length >= 6 &&
                trimmedPassword.any { it.isLetter() } &&
                trimmedPassword.any { it.isDigit() }

        _uiState.update { current ->
            val passwordError = if (current.passwordError != null && valid) null else current.passwordError

            current.copy(
                password = trimmedPassword,
                isButtonEnabled = current.email.isNotBlank() && trimmedPassword.isNotBlank(),
                passwordError = passwordError
            )
        }
        savedStateHandle["password"] = trimmedPassword
    }

    fun resetState() {
        _uiState.value = LoginUiState()
        savedStateHandle["email"] = ""
        savedStateHandle["password"] = ""
    }

    fun validateEmailOnFocusChange() {
        val email = _uiState.value.email.trim()

        if (email.isEmpty()) return

        _uiState.update {
            it.copy(
                emailErrorType = if (!isEmailValid(email)) EmailErrorType.INVALID else null
            )
        }
    }

    fun onLoginClick(): Boolean {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (!isEmailValid(email) || !isPasswordValid(password)) {
            showValidationErrors(email, password)
            return false
        }
        return true
    }


    fun setLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }

    fun buttonControl() {
        _uiState.update {
            it.copy(
                isButtonEnabled = !uiState.value.isButtonEnabled
            )
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6 &&
                password.any { it.isLetter() } &&
                password.any { it.isDigit() }
    }

    private fun showValidationErrors(email: String, password: String) {
        val emailValid = isEmailValid(email)
        val passwordValid = isPasswordValid(password)

        _uiState.update {
            it.copy(
                emailErrorType = if (!emailValid) EmailErrorType.INVALID else null,
                passwordErrorType = if (!passwordValid) PasswordErrorType.INVALID else null
            )
        }
    }
}
