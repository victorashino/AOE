package dev.bicutoru.aoe.presentation.login

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.bicutoru.aoe.R
import dev.bicutoru.aoe.core.nav.Routes
import dev.bicutoru.aoe.presentation.auth.AuthState
import dev.bicutoru.aoe.presentation.auth.AuthViewModel
import dev.bicutoru.aoe.presentation.common.ui.ComponentDimens
import dev.bicutoru.aoe.presentation.common.ui.DeviceConfiguration
import dev.bicutoru.aoe.presentation.common.ui.FontSize
import dev.bicutoru.aoe.presentation.common.ui.Radius
import dev.bicutoru.aoe.presentation.common.ui.ScreenDimens
import dev.bicutoru.aoe.presentation.login.components.CustomButton
import dev.bicutoru.aoe.presentation.login.components.CustomPasswordField
import dev.bicutoru.aoe.presentation.login.components.CustomTextField
import dev.bicutoru.aoe.ui.theme.bold

@SuppressLint("ConfigurationScreenWidthHeight", "UseOfNonLambdaOffsetOverload",
    "UnrememberedGetBackStackEntry"
)
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Idle -> {
                navController.navigate(Routes.PAYMENTS_SCREEN) { launchSingleTop = true }
            }
            is AuthState.Error -> {
                loginViewModel.setLoading(false)
                loginViewModel.buttonControl()
                snackbarHostState.showSnackbar(
                    message = (authState as AuthState.Error).message,
                    duration = SnackbarDuration.Short
                )
                authViewModel.resetState()
                loginViewModel.buttonControl()
            }
            else -> Unit
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            loginViewModel.setLoading(false)
        }
    }

    fun handleLogin() {
        val isValid = loginViewModel.onLoginClick()
        if (isValid && uiState.isButtonEnabled) {
            loginViewModel.setLoading(true)
            authViewModel.login()
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)
    val isKeyboardVisible = imeBottom > 0
    val offset by animateDpAsState(
        targetValue = if (isKeyboardVisible) screenHeight * 0.09f else ComponentDimens.ZeroPadding,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentWindowInsets = WindowInsets.statusBars,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(ComponentDimens.SmallPadding)
                    .imePadding()
                    .widthIn(ComponentDimens.MaxSnackBarWidth)
            )
        }
    ) { innerPadding ->

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.55f))
            }
            return@Scaffold
        }

        val rootModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = ScreenDimens.HorizontalPadding)
            .consumeWindowInsets(WindowInsets.navigationBars)

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        val headerModifier = when (deviceConfiguration) {
            DeviceConfiguration.TABLET_PORTRAIT, DeviceConfiguration.TABLET_LANDSCAPE ->
                Modifier.widthIn(max = ComponentDimens.MaxComponentWidth)
            else -> Modifier.fillMaxWidth()
        }

        val formModifier = when (deviceConfiguration) {
            DeviceConfiguration.TABLET_PORTRAIT, DeviceConfiguration.TABLET_LANDSCAPE ->
                Modifier.widthIn(max = ComponentDimens.MaxComponentWidth)
            else -> Modifier.fillMaxWidth()
        }

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                Row(
                    modifier = rootModifier.windowInsetsPadding(WindowInsets.displayCutout),
                    horizontalArrangement = Arrangement.spacedBy(ComponentDimens.LargeSpacedBy)
                ) {
                    LoginHeaderSection(modifier = Modifier.weight(1f), isKeyboardVisible = false)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.Center
                    ) {
                        LoginFormSection(
                            uiState = uiState,
                            emailText = uiState.email,
                            onEmailTextChange = loginViewModel::onEmailChange,
                            passwordText = uiState.password,
                            onPasswordTextChange = loginViewModel::onPasswordChange,
                            onClickButton = { handleLogin() },
                            onPasswordFieldFocused = { loginViewModel.validateEmailOnFocusChange() },
                            modifier = formModifier
                        )
                    }
                }
            }

            else -> { // Mobile Portrait & Tablets
                Column(
                    modifier = rootModifier
                        .offset(y = -offset)
                        .verticalScroll(scrollState)
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(ComponentDimens.LargeSpacedBy)
                ) {
                    LoginHeaderSection(modifier = headerModifier, isKeyboardVisible = isKeyboardVisible)
                    LoginFormSection(
                        uiState = uiState,
                        emailText = uiState.email,
                        onEmailTextChange = loginViewModel::onEmailChange,
                        passwordText = uiState.password,
                        onPasswordTextChange = loginViewModel::onPasswordChange,
                        onClickButton = { handleLogin() },
                        onPasswordFieldFocused = { loginViewModel.validateEmailOnFocusChange() },
                        modifier = formModifier
                    )
                }
            }
        }
    }
}


@Composable
fun LoginHeaderSection(
    modifier: Modifier,
    isKeyboardVisible: Boolean,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.bold.copy(
                fontSize = FontSize.FontLarge,
                color = if (isKeyboardVisible) Color.Transparent else MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .padding(bottom = ComponentDimens.LargePadding)
                .align(alignment)
        )

        Image(
            painter = painterResource(R.drawable.aoe),
            contentDescription = stringResource(R.string.logo_content_description),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Radius.Medium))
        )
    }
}

@Composable

fun LoginFormSection(
    uiState: LoginUiState,
    emailText: String,
    onEmailTextChange: (String) -> Unit,
    passwordText: String,
    onPasswordTextChange: (String) -> Unit,
    onClickButton: () -> Unit,
    onPasswordFieldFocused: () -> Unit,
    modifier: Modifier,
) {

    val emailErrorText = when (uiState.emailErrorType) {
        EmailErrorType.INVALID -> stringResource(R.string.invalid_email)
        else -> null
    }

    val passwordErrorText = when (uiState.passwordErrorType) {
        PasswordErrorType.INVALID -> stringResource(R.string.invalid_password)
        else -> null
    }

    Column(modifier = modifier) {
        CustomTextField(
            label = stringResource(R.string.email),
            value = emailText,
            onValueChange = onEmailTextChange,
            error = emailErrorText,
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(ComponentDimens.MediumPadding))

        CustomPasswordField(
            label = stringResource(R.string.password),
            value = passwordText,
            onValueChange = onPasswordTextChange,
            error = passwordErrorText,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) onPasswordFieldFocused()
                }
        )

        Spacer(modifier = Modifier.height(ComponentDimens.MediumPadding))

        CustomButton(
            onClick = onClickButton,
            text = stringResource(R.string.login),
            state = uiState.isButtonEnabled,
            modifier = modifier.fillMaxWidth()
        )
    }
}