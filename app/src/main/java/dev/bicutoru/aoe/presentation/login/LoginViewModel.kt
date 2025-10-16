package dev.bicutoru.aoe.presentation.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    fun performLogin() {

    }

    fun isValidPassword(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        return password.length >= 6 && hasLetter && hasDigit
    }

    fun isValidEmail(email: String): Boolean = (email.contains("@"))

}