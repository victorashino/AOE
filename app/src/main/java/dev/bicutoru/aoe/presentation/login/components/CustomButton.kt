package dev.bicutoru.aoe.presentation.login.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.bicutoru.aoe.presentation.common.ui.ComponentDimens
import dev.bicutoru.aoe.presentation.common.ui.FontSize
import dev.bicutoru.aoe.presentation.common.ui.Radius
import dev.bicutoru.aoe.ui.theme.bold

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    state: Boolean = false,
    modifier: Modifier
) {

    val colors = MaterialTheme.colorScheme
    val interactionSource = remember { MutableInteractionSource() }

    Button(
        modifier = modifier
            .imePadding()
            .height(ComponentDimens.ButtonHeight)
            .navigationBarsPadding(),
        onClick = { if (state) onClick() },
        interactionSource = interactionSource,
        shape = RoundedCornerShape(Radius.Small),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state) colors.onSurface else colors.primary,
            contentColor = if (state) colors.background else colors.primary,
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bold.copy(
                fontSize = FontSize.FontMedium
            )
        )
    }
}
