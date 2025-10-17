package dev.bicutoru.aoe.data.repository

import dev.bicutoru.aoe.data.datasource.remote.LoginService
import dev.bicutoru.aoe.data.mapper.toDomain
import dev.bicutoru.aoe.domain.model.UserInfos
import dev.bicutoru.aoe.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : AuthRepository {

    override suspend fun login(): UserInfos {
        return try {
            val responseList = loginService.login()
            val firstUser = responseList.firstOrNull()
                ?: throw Exception("Nenhum usuário encontrado")
            firstUser.toDomain()
        } catch (e: Exception) {
            throw e
        }
    }
}