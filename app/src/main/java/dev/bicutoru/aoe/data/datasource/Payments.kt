package dev.bicutoru.aoe.data.datasource

data class Payments(
    val payments: List<Payment>
)

data class Payment(
    val paymentDate: String,
    val electricityBill: String,
    val id: Int
)
