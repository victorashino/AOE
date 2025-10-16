package dev.bicutoru.aoe.data.datasource.remote.dto

data class UserInfosDTO(
    val customerName: String,
    val accountNumber: String,
    val branchNumber: String,
    val checkingAccountBalance: Int,
    val id: String
)