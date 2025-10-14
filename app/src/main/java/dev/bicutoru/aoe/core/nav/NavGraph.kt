package dev.bicutoru.aoe.core.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.bicutoru.aoe.presentation.login.LoginScreen
import dev.bicutoru.aoe.presentation.payments.PaymentsScreen

@Composable
fun NavGraph(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN_SCREEN,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Routes.LOGIN_SCREEN) {
            LoginScreen(navController)
        }

        composable(route = Routes.PAYMENTS_SCREEN) {
            PaymentsScreen(navController)
        }
    }
}
