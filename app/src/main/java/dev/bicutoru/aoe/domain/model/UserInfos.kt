package dev.bicutoru.aoe.domain.model

data class UserInfos(
    val customerName: String,
    val accountNumber: String,
    val branchNumber: String,
    val checkingAccountBalance: Int,
    val id: String
)