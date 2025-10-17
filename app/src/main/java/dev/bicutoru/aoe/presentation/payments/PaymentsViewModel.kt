package dev.bicutoru.aoe.presentation.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bicutoru.aoe.data.repository.PaymentsRepositoryImpl
import dev.bicutoru.aoe.domain.model.Payment
import dev.bicutoru.aoe.domain.model.UserInfos
import dev.bicutoru.aoe.domain.repository.PaymentsRepository
import dev.bicutoru.aoe.presentation.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val repository: PaymentsRepositoryImpl
) : ViewModel() {

    private val _paymentsState = MutableStateFlow<PaymentsState>(PaymentsState.Empty)
    val paymentsState: StateFlow<PaymentsState> = _paymentsState

    init {
        getPayments()
    }

    private fun getPayments() {
        viewModelScope.launch {
            try {
                val response: List<Payment> = repository.getAllPayments()
                _paymentsState.value = PaymentsState.Idle(response)
            } catch (e: Exception) {
                _paymentsState.value = PaymentsState.Error(e.message ?: "Erro! Tente novamente mais tarde.")
            }
        }
    }
}