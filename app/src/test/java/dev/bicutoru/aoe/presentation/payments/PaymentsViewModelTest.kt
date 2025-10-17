package dev.bicutoru.aoe.presentation.payments

import dev.bicutoru.aoe.data.repository.PaymentsRepositoryImpl
import dev.bicutoru.aoe.domain.model.Payment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentsViewModelTest {

    private lateinit var repository: PaymentsRepositoryImpl
    private lateinit var viewModel: PaymentsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(PaymentsRepositoryImpl::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should call getPayments and set Idle state with data`() = runTest {
        val fakePayments = listOf(
            Payment(id = "1", date = "01/05/2024", amount = "R$ 3214,42"),
            Payment(id = "2", date = "12/11/2022", amount = "R$ 431,31"),
        )
        `when`(repository.getAllPayments()).thenReturn(fakePayments)

        viewModel = PaymentsViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.paymentsState.first()
        assertTrue(state is PaymentsState.Idle)
        assertEquals(fakePayments, (state as PaymentsState.Idle).payments)
        verify(repository).getAllPayments()
    }

    @Test
    fun `getPayments should emit Error state on exception`() = runTest {
        `when`(repository.getAllPayments()).thenThrow(RuntimeException("Network error"))

        viewModel = PaymentsViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.paymentsState.first()
        assertTrue(state is PaymentsState.Error)
        assertEquals("Network error", (state as PaymentsState.Error).message)
        verify(repository).getAllPayments()
    }
}
