package dev.bicutoru.aoe.presentation.login

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel()
    }

    @Test
    fun `initial state should have empty email and password`() = runTest {
        val state = viewModel.uiState.first()
        assertEquals("", state.email)
        assertEquals("", state.password)
        assertFalse(state.isButtonEnabled)
    }

    @Test
    fun `onEmailChange updates email and button state`() = runTest {
        viewModel.onPasswordChange("A123456")
        viewModel.onEmailChange("email@teste.com")

        val state = viewModel.uiState.first()
        assertEquals("email@teste.com", state.email)
        assertTrue(state.isButtonEnabled)
    }

    @Test
    fun `onPasswordChange updates password and button state`() = runTest {
        viewModel.onEmailChange("user@domain.com")
        viewModel.onPasswordChange("123456a")

        val state = viewModel.uiState.first()
        assertEquals("123456a", state.password)
        assertTrue(state.isButtonEnabled)
    }

    @Test
    fun `onLoginClick with invalid email returns false`() = runTest {
        viewModel.onEmailChange("email_invalido")
        viewModel.onPasswordChange("A123456")

        val result = viewModel.onLoginClick()
        val state = viewModel.uiState.first()

        assertFalse(result)
        assertNotNull(state.emailErrorType)
    }

    @Test
    fun `onLoginClick with valid credentials returns true`() = runTest {
        viewModel.onEmailChange("email@teste.com")
        viewModel.onPasswordChange("A123456")

        val result = viewModel.onLoginClick()
        val state = viewModel.uiState.first()

        assertTrue(result)
        assertNull(state.emailErrorType)
        assertNull(state.passwordErrorType)
    }

    @Test
    fun `resetState resets uiState to default`() = runTest {
        viewModel.onEmailChange("user@domain.com")
        viewModel.onPasswordChange("123456a")
        viewModel.setLoading(true)

        viewModel.resetState()
        val state = viewModel.uiState.first()

        assertEquals(LoginUiState(), state)
    }

    @Test
    fun `buttonControl toggles button state`() = runTest {
        val initial = viewModel.uiState.first().isButtonEnabled
        viewModel.buttonControl()
        val updated = viewModel.uiState.first().isButtonEnabled

        assertNotEquals(initial, updated)
    }
}
