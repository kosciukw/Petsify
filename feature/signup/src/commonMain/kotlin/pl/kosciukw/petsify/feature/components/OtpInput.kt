package pl.kosciukw.petsify.feature.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS

@Composable
fun OtpInput(
    value: String,
    otpLength: Int = 6,
    onOtpChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val normalizedValue = remember(value, otpLength) {
        value.filter { it.isDigit() }.take(otpLength)
    }

    BasicTextField(
        value = normalizedValue,
        onValueChange = { newValue ->
            onOtpChanged(newValue.filter { it.isDigit() }.take(otpLength))
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.background),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            focusRequester.requestFocus()
                        },
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(otpLength) { index ->
                        OtpCell(value = normalizedValue.getOrNull(index)?.toString().orEmpty())
                    }
                }

                Box(
                    modifier = Modifier
                        .size(1.dp)
                        .alpha(0f)
                ) {
                    innerTextField()
                }
            }
        }
    )
}

@Composable
private fun OtpCell(value: String) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = if (value.isBlank()) GoshawkGrey else MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(18.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            style = TextRegularS.copy(textAlign = TextAlign.Center)
        )
    }
}
