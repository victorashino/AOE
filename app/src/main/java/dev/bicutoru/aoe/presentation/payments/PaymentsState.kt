package dev.bicutoru.aoe.presentation.payments

import dev.bicutoru.aoe.domain.model.Payment

sealed class PaymentsState {
    data object Empty : PaymentsState()
    data class Idle(val payments: List<Payment>) : PaymentsState()
    data class Error(val message: String) : PaymentsState()
}