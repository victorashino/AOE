package dev.bicutoru.aoe.data.mapper

import dev.bicutoru.aoe.data.datasource.remote.dto.UserInfosDTO
import dev.bicutoru.aoe.domain.model.UserInfos


fun UserInfosDTO.toDomain(): UserInfos {
    return UserInfos(
        customerName = customerName,
        accountNumber = accountNumber,
        branchNumber = branchNumber,
        checkingAccountBalance = checkingAccountBalance,
        id = id
    )
}