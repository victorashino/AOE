package dev.bicutoru.aoe.presentation.auth

import dev.bicutoru.aoe.domain.model.UserInfos

sealed class AuthState {
    data object Empty : AuthState()
    data class Idle(val user: UserInfos) : AuthState()
    data class Error(val message: String) : AuthState()
}