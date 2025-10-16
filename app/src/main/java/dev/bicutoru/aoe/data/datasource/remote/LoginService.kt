package dev.bicutoru.aoe.data.datasource.remote

import dev.bicutoru.aoe.data.datasource.remote.dto.UserInfosDTO
import retrofit2.http.GET

interface LoginService {

    @GET("login")
    suspend fun login(): UserInfosDTO

}