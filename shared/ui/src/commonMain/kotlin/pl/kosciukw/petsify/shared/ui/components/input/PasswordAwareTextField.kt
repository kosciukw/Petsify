package pl.kosciukw.petsify.shared.ui.components.input

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordAwareTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    trailingIcon: Painter? = null,
    errorMessage: String? = null,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    var isPasswordVisible by rememberSaveable(isPassword) { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        trailingContent = {
            trailingIcon?.let {
                PasswordToggleIcon(
                    painter = it,
                    isPassword = isPassword,
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
                )
            }
        },
        isError = isError,
        errorMessage = errorMessage,
        keyboardType = keyboardType,
        visualTransformation = when {
            isPassword && !isPasswordVisible -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        }
    )
}

@Composable
internal fun PasswordToggleIcon(
    painter: Painter,
    isPassword: Boolean,
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    val iconModifier = Modifier
        .size(24.dp)
        .padding(4.dp)

    if (!isPassword) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = iconModifier
        )
        return
    }

    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = iconModifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onToggleVisibility
        ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
            modifier = Modifier.matchParentSize()
        )

        if (!isPasswordVisible) {
            HiddenPasswordBadge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 10.dp, y = (-8).dp)
            )
        }
    }
}

@Composable
internal fun HiddenPasswordBadge(
    modifier: Modifier = Modifier
) {
    val containerColor = MaterialTheme.colorScheme.surface
    val contentColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .size(12.dp)
            .background(containerColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(8.dp)) {
            val strokeWidth = 1.dp.toPx()
            val shackleRadius = size.width * 0.22f
            val shackleCenterY = size.height * 0.33f
            val bodyTop = size.height * 0.42f

            drawArc(
                color = contentColor,
                startAngle = 200f,
                sweepAngle = 140f,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(
                    x = size.width * 0.28f,
                    y = shackleCenterY - shackleRadius
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width * 0.44f,
                    height = size.height * 0.38f
                ),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawRoundRect(
                color = contentColor,
                topLeft = androidx.compose.ui.geometry.Offset(
                    x = size.width * 0.24f,
                    y = bodyTop
                ),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width * 0.52f,
                    height = size.height * 0.34f
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.5.dp.toPx())
            )
        }
    }
}
