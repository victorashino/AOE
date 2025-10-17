package dev.bicutoru.aoe.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dev.bicutoru.aoe.presentation.common.ui.FontSize
import dev.bicutoru.aoe.presentation.common.ui.Radius
import dev.bicutoru.aoe.ui.theme.normal

@Composable
fun CustomPasswordField(
    label: String,
    value: String,
    error: String? = null,
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
        visualTransformation = PasswordVisualTransformation(),
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
            unfocusedTextColor = colors.primary,
            errorTextColor = colors.secondary
        ),
        modifier = modifier
            .background(colors.tertiary, RoundedCornerShape(Radius.Small)),
        isError = error != null,
    )

    if (error != null) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}
