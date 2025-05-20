package pl.kosciukw.petsify.shared.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.ui.components.shapes.RoundedCornerShapeM
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS

@Composable
fun ButtonRegular(
    modifier: Modifier = Modifier,
    style: TextStyle = TextBoldS,
    label: String,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer
    ),
    textColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    onClick: () -> Unit,
    isButtonEnabled: Boolean = true
) {
    Button(
        onClick = { onClick() },
        colors = buttonColors,
        modifier = modifier,
        enabled = isButtonEnabled
    ) {
        Text(
            style = style,
            text = label,
            color = textColor
        )
    }
}

@Composable
fun ButtonContent(
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer
    ),
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShapeM,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        colors = buttonColors,
        modifier = modifier,
        shape = shape,
        contentPadding = contentPadding
    ) {
        content()
    }
}

@Composable
fun ButtonText(
    modifier: Modifier = Modifier,
    textColor: Color = GoshawkGrey,
    style: TextStyle = TextBoldS,
    label: String,
    onClick: () -> Unit
) {
    Text(
        text = label,
        color = textColor,
        style = style,
        modifier = modifier.clickable { onClick() }
    )
}

@Composable
fun CircleButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    size: Dp = 48.dp
) {
    Surface(
        modifier = modifier.size(size),
        shape = CircleShape,
        color = backgroundColor,
        shadowElevation = 4.dp
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = contentColor
            )
        }
    }
}