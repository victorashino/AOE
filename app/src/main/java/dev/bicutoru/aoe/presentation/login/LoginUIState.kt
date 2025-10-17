package dev.bicutoru.aoe.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailErrorType: EmailErrorType? = null,
    val passwordErrorType: PasswordErrorType? = null,
    val isButtonEnabled: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false
)


enum class EmailErrorType { INVALID }
enum class PasswordErrorType { INVALID }