package pl.kosciukw.petsify.shared.ui.components.logo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.utils.empty

@Composable
fun TextWithTrailingIcon(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    text: String,
    textColor: Color,
    resId: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )

        Icon(
            painter = painterResource(id = resId),
            contentDescription = String.empty(),
            tint = Color.Black
        )
    }
}