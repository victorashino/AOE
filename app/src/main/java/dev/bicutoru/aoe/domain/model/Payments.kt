package dev.bicutoru.aoe.domain.model

data class Payments(
    val payments: List<Payment>
)

data class Payment(
    val id: String,
    val date: String,
    val amount: String
)
