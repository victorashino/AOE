package dev.bicutoru.aoe.data.mapper

import dev.bicutoru.aoe.data.datasource.remote.dto.PaymentsDTO
import dev.bicutoru.aoe.domain.model.Payment
import dev.bicutoru.aoe.domain.model.Payments

fun PaymentsDTO.toDomain(): Payment {
    return Payment(
        id = this.id,
        date = this.paymentDate,
        amount = this.electricityBill
    )
}

fun List<PaymentsDTO>.toDomainList(): List<Payment> {
    return this.map { it.toDomain() }
}
