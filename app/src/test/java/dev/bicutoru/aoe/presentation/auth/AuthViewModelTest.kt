package dev.bicutoru.aoe.presentation.auth

import dev.bicutoru.aoe.data.repository.AuthRepositoryImpl
import dev.bicutoru.aoe.domain.model.UserInfos
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
class AuthViewModelTest {

    private lateinit var repository: AuthRepositoryImpl
    private lateinit var viewModel: AuthViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(AuthRepositoryImpl::class.java)
        viewModel = AuthViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login should emit Idle state with user info on success`() = runTest {

        val fakeUser = UserInfos(
            customerName = "Alice",
            accountNumber ="1423",
            branchNumber = "6788",
            checkingAccountBalance = 1423412,
            id = "33"
        )
        `when`(repository.login()).thenReturn(fakeUser)

        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.authState.first()
        assertTrue(state is AuthState.Idle)
        assertEquals(fakeUser, (state as AuthState.Idle).user)
        verify(repository).login()
    }

    @Test
    fun `login should emit Error state when repository throws exception`() = runTest {
        `when`(repository.login()).thenThrow(RuntimeException("Invalid credentials"))

        viewModel.login()
        advanceUntilIdle()

        val state = viewModel.authState.first()
        assertTrue(state is AuthState.Error)
        assertEquals("Invalid credentials", (state as AuthState.Error).message)
        verify(repository).login()
    }

    @Test
    fun `resetState should set state to Empty`() = runTest {
        viewModel.login()
        advanceUntilIdle()
        viewModel.resetState()

        val state = viewModel.authState.first()
        assertTrue(state is AuthState.Empty)
    }
}
