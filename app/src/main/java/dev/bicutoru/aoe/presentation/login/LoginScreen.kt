package dev.bicutoru.aoe.presentation.login

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.bicutoru.aoe.R
import dev.bicutoru.aoe.presentation.common.ui.ComponentDimens
import dev.bicutoru.aoe.presentation.common.ui.DeviceConfiguration
import dev.bicutoru.aoe.presentation.common.ui.FontSize
import dev.bicutoru.aoe.presentation.common.ui.Radius
import dev.bicutoru.aoe.presentation.common.ui.ScreenDimens
import dev.bicutoru.aoe.presentation.login.components.CustomButton
import dev.bicutoru.aoe.presentation.login.components.CustomPasswordField
import dev.bicutoru.aoe.presentation.login.components.CustomTextField
import dev.bicutoru.aoe.ui.theme.bold

@SuppressLint("ConfigurationScreenWidthHeight", "UseOfNonLambdaOffsetOverload")
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)
    val isKeyboardVisible = imeBottom > 0
    val offset by animateDpAsState(
        targetValue = if (isKeyboardVisible) screenHeight * 0.09f else ComponentDimens.ZeroPadding,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->

        val rootModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = ScreenDimens.HorizontalPadding)
            .consumeWindowInsets(WindowInsets.navigationBars)

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                Column(
                    modifier = rootModifier
                        .offset(y = -offset)
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(ComponentDimens.LargeSpacedBy)
                ) {
                    LoginHeaderSection(modifier = Modifier.fillMaxWidth(), isKeyboardVisible)
                    LoginFormSection(
                        emailText = emailState,
                        onEmailTextChange = { emailState = it.trim() },
                        passwordText = passwordState,
                        onPasswordTextChange = { passwordState = it.trim() },
                        modifier = Modifier.fillMaxWidth()

                    )
                }
            }

            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                Row(
                    modifier = rootModifier
                        .windowInsetsPadding(WindowInsets.displayCutout),
                    horizontalArrangement = Arrangement.spacedBy(ComponentDimens.LargeSpacedBy)
                ) {
                    LoginHeaderSection(modifier = Modifier.weight(1f), false)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center
                    ) {
                        LoginFormSection(
                            emailText = emailState,
                            onEmailTextChange = { emailState = it.trim() },
                            passwordText = passwordState,
                            onPasswordTextChange = { passwordState = it.trim() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE -> {
                Column(
                    modifier = rootModifier
                        .offset(y = -offset)
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(ComponentDimens.LargeSpacedBy)
                ) {
                    LoginHeaderSection(
                        modifier = Modifier.widthIn(max = ComponentDimens.MaxComponentWidth),
                        isKeyboardVisible = isKeyboardVisible
                    )
                    LoginFormSection(
                        emailText = emailState,
                        onEmailTextChange = { emailState = it.trim() },
                        passwordText = passwordState,
                        onPasswordTextChange = { passwordState = it.trim() },
                        modifier = Modifier.widthIn(max = ComponentDimens.MaxComponentWidth)

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
    emailText: String,
    onEmailTextChange: (String) -> Unit,
    passwordText: String,
    onPasswordTextChange: (String) -> Unit,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        CustomTextField(
            label = stringResource(R.string.email),
            value = emailText,
            onValueChange = onEmailTextChange,
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(ComponentDimens.MediumPadding))

        CustomPasswordField(
            label = stringResource(R.string.password),
            value = passwordText,
            onValueChange = onPasswordTextChange,
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(ComponentDimens.MediumPadding))

        CustomButton(
            onClick = {},
            text = stringResource(R.string.login),
            state = true,
            modifier = modifier.fillMaxWidth()
        )
    }
}