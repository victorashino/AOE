package dev.bicutoru.aoe.data.repository

import dev.bicutoru.aoe.data.datasource.remote.PaymentsService
import dev.bicutoru.aoe.data.mapper.toDomainList
import dev.bicutoru.aoe.domain.model.Payment
import dev.bicutoru.aoe.domain.repository.PaymentsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentsRepositoryImpl @Inject constructor(
    private val paymentsService: PaymentsService
) : PaymentsRepository {
    override suspend fun getAllPayments(): List<Payment> {
        val responseList = paymentsService.getPayments()
        return responseList.toDomainList()
    }
}