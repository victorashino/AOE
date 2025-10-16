package dev.bicutoru.aoe.domain.model

data class Payments(
    val payments: List<Payment>
)

data class Payment(
    val paymentDate: String,
    val electricityBill: String,
    val id: Int
)
