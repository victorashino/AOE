package dev.bicutoru.aoe.data.datasource.remote.dto

import dev.bicutoru.aoe.domain.model.Payments


data class PaymentsDTO(
    val id: String,
    val paymentDate: String,
    val electricityBill: String
)