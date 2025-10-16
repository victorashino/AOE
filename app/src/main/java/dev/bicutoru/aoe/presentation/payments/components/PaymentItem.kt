package dev.bicutoru.aoe.presentation.payments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.bicutoru.aoe.R
import dev.bicutoru.aoe.domain.model.Payment
import dev.bicutoru.aoe.presentation.common.ui.ComponentDimens
import dev.bicutoru.aoe.presentation.common.ui.FontSize
import dev.bicutoru.aoe.ui.theme.medium
import dev.bicutoru.aoe.ui.theme.normal

@Composable
fun PaymentItem(
    payment: Payment,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.electricity_bill),
                style = MaterialTheme.typography.medium.copy(fontSize = FontSize.FontMedium)
            )

            Text(
                text = payment.electricityBill,
                style = MaterialTheme.typography.normal.copy(
                    fontSize = FontSize.FontSmall,
                    color = MaterialTheme.colorScheme.onPrimaryFixedVariant
                ),
                modifier = Modifier.padding(top = ComponentDimens.SmallPadding)
            )
        }

        Text(
            text = payment.paymentDate,
            style = MaterialTheme.typography.normal.copy(
                fontSize = FontSize.FontSmall,
                color = MaterialTheme.colorScheme.onPrimaryFixedVariant
            )
        )
    }
}
