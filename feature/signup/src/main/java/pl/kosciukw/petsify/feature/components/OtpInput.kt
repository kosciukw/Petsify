package pl.kosciukw.petsify.feature.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import pl.kosciukw.petsify.shared.ui.components.shapes.RoundedCornerShapeM
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.utils.empty

@Composable
fun OtpInput(
    otpLength: Int = 4,
    onOtpEntered: (String) -> Unit
) {
    var otpValues by remember { mutableStateOf(List(otpLength) { String.empty() }) }
    val focusRequesters = List(otpLength) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        otpValues.forEachIndexed { index, value ->
            BasicTextField(
                value = value,
                onValueChange = { new ->
                    if (new.length <= 1 && new.all { it.isDigit() }) {
                        otpValues = otpValues.toMutableList().also { it[index] = new }

                        if (new.isNotEmpty() && index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if (otpValues.all { it.isNotEmpty() }) {
                            onOtpEntered(otpValues.joinToString(String.empty()))
                        }
                    }
                },
                modifier = Modifier
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShapeM)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShapeM)
                    .focusRequester(focusRequesters[index])
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textStyle = TextRegularS,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword
                )
            )
        }
    }
}
