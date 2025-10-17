package dev.bicutoru.aoe.data.datasource.remote

import dev.bicutoru.aoe.data.datasource.remote.dto.PaymentsDTO
import retrofit2.http.GET

interface PaymentsService {

    @GET("payments")
    suspend fun getPayments(): List<PaymentsDTO>

}