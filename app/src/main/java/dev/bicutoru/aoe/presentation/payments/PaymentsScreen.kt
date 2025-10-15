package dev.bicutoru.aoe.presentation.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.bicutoru.aoe.presentation.common.ui.ScreenDimens

@Composable
fun PaymentsScreen(
    navController: NavHostController,
    viewModel: PaymentsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = ScreenDimens.HorizontalPadding)
            .background(Color.Black)
    ) {

    }

}