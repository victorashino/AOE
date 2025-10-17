package dev.bicutoru.aoe.domain.repository

import dev.bicutoru.aoe.domain.model.UserInfos

interface AuthRepository {
    suspend fun login(): UserInfos
}