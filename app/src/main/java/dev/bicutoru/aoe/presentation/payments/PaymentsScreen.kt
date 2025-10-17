package dev.bicutoru.aoe.presentation.payments

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.bicutoru.aoe.R
import dev.bicutoru.aoe.core.nav.Routes
import dev.bicutoru.aoe.domain.model.Payment
import dev.bicutoru.aoe.domain.model.Payments
import dev.bicutoru.aoe.domain.model.UserInfos
import dev.bicutoru.aoe.presentation.auth.AuthState
import dev.bicutoru.aoe.presentation.auth.AuthViewModel
import dev.bicutoru.aoe.presentation.common.ui.ComponentDimens
import dev.bicutoru.aoe.presentation.common.ui.DeviceConfiguration
import dev.bicutoru.aoe.presentation.common.ui.FontSize
import dev.bicutoru.aoe.presentation.common.ui.ScreenDimens
import dev.bicutoru.aoe.presentation.payments.components.PaymentItem
import dev.bicutoru.aoe.ui.theme.bold
import dev.bicutoru.aoe.ui.theme.medium
import dev.bicutoru.aoe.ui.theme.normal
import java.util.Locale

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun PaymentsScreen(
    navController: NavHostController
) {
    val authViewModel: AuthViewModel = hiltViewModel(navController.getBackStackEntry(Routes.LOGIN_SCREEN))
    val authState by authViewModel.authState.collectAsState()

    BackHandler {
        authViewModel.resetState()
        navController.popBackStack()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        val payments = Payments(
            listOf(
                Payment("01/03/2004", electricityBill = "R$400,00", id = 0),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 1),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 2),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 3),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 4),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 5),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 6),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 7),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 8),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 9),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 10),
                Payment("01/03/2004", electricityBill = "R$400,00", id = 11),
            )
        )

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT,
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE -> {

                when (authState) {
                    is AuthState.Empty -> Unit

                    is AuthState.Idle -> {
                        val userInfos = (authState as AuthState.Idle).user
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            PaymentsHeader(
                                modifier = Modifier,
                                onBackClick = {
                                    authViewModel.resetState()
                                    navController.popBackStack()
                                }
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = ScreenDimens.HorizontalPadding)
                                    .consumeWindowInsets(WindowInsets.navigationBars)
                                    .widthIn(max = ComponentDimens.MaxComponentWidth)
                                    .align(Alignment.CenterHorizontally),
                                contentPadding = PaddingValues(
                                    bottom = ComponentDimens.LargePadding
                                ),
                                verticalArrangement = Arrangement.spacedBy(ComponentDimens.MediumPadding)
                            ) {
                                item {
                                    PaymentsUserDetailsSection(
                                        modifier = Modifier.fillMaxWidth(),
                                        userInfos = userInfos
                                    )

                                    Text(
                                        text = stringResource(R.string.bills_paid),
                                        style = MaterialTheme.typography.bold.copy(
                                            fontSize = FontSize.FontExtraLarge
                                        ),
                                        modifier = Modifier.padding(bottom = ComponentDimens.MediumPadding)
                                    )
                                }

                                items(
                                    items = payments.payments,
                                    key = { it.id }
                                ) { payment ->
                                    PaymentItem(
                                        payment = payment,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }

                    is AuthState.Error -> {
                        val errorMessage = (authState as AuthState.Error).message
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentsHeader(
    modifier: Modifier,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.back)
            )
        }

        Text(
            text = stringResource(R.string.payments),
            style = MaterialTheme.typography.bold.copy(
                fontSize = FontSize.FontLarge
            ),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}


@Composable
fun PaymentsUserDetailsSection(
    userInfos: UserInfos,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(
            top = ComponentDimens.MediumPadding,
            bottom = ComponentDimens.LargePadding
        )
    ) {
        Text(
            text = stringResource(R.string.payment_details),
            style = MaterialTheme.typography.bold.copy(
                fontSize = FontSize.FontExtraLarge
            ),
            modifier = Modifier.padding(bottom = ComponentDimens.MediumPadding)
        )

        val balance: Float = userInfos.checkingAccountBalance / 100f

        val formattedBalance = NumberFormat
            .getNumberInstance(
                Locale(
                    stringResource(R.string.pt),
                    stringResource(R.string.br)
                )
            ).apply {
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }.format(balance)

        Text(
            text = stringResource(R.string.client_label, userInfos.customerName),
            style = MaterialTheme.typography.medium.copy(
                fontSize = FontSize.FontMedium
            ),
            modifier = Modifier.padding(bottom = ComponentDimens.TinyPadding)
        )

        Text(
            text = stringResource(
                R.string.agency_and_account_label,
                userInfos.branchNumber,
                userInfos.accountNumber
            ),
            style = MaterialTheme.typography.normal.copy(
                fontSize = FontSize.FontSmall,
                color = MaterialTheme.colorScheme.onPrimaryFixedVariant
            ),
            modifier = Modifier.padding(bottom = ComponentDimens.MediumPadding)
        )

        Text(
            text = stringResource(R.string.balance_label, formattedBalance),
            style = MaterialTheme.typography.normal.copy(
                fontSize = FontSize.FontMedium
            ),
            modifier = Modifier
        )
    }
}
