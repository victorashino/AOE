package dev.bicutoru.aoe.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import dev.bicutoru.aoe.R

val Inter = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
)

val Typography = Typography()

val Typography.bold: TextStyle
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.3f.em
    )

val Typography.medium: TextStyle
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        lineHeight = 1.3f.em
    )

val Typography.normal: TextStyle
    get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.3f.em
    )