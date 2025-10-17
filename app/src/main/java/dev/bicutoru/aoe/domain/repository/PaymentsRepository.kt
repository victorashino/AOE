package dev.bicutoru.aoe.domain.repository

import dev.bicutoru.aoe.domain.model.Payment

interface PaymentsRepository {
    suspend fun getAllPayments(): List<Payment>
}