package dev.bicutoru.aoe.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.bicutoru.aoe.presentation.common.ui.FontSize
import dev.bicutoru.aoe.presentation.common.ui.Radius
import dev.bicutoru.aoe.ui.theme.normal

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier
) {

    val colors = MaterialTheme.colorScheme

    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.normal,
        singleLine = singleLine,
        shape = RoundedCornerShape(Radius.Small),
        label = if (value.isEmpty()) {
            {
                Text(
                    text = label,
                    style = MaterialTheme.typography.normal.copy(fontSize = FontSize.FontMedium),
                    color = colors.primary
                )
            }
        } else null,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = colors.primary,
            focusedTextColor = colors.secondary,
            unfocusedTextColor = colors.primary
        ),
        modifier = modifier
            .background(colors.tertiary, RoundedCornerShape(Radius.Small)),
    )
}