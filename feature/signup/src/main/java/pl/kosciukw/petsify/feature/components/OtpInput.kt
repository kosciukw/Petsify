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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.components.shapes.RoundedCornerShapeM
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.utils.empty

@Composable
fun OtpInput(
    value: String,
    otpLength: Int = 6,
    onOtpChanged: (String) -> Unit
) {
    val clippedValue = remember(value, otpLength) { value.take(otpLength) }
    val otpValues = remember(clippedValue, otpLength) {
        List(otpLength) { index -> clippedValue.getOrNull(index)?.toString() ?: String.empty() }
    }
    val focusRequesters = List(otpLength) { FocusRequester() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        otpValues.forEachIndexed { index, cellValue ->
            BasicTextField(
                value = cellValue,
                onValueChange = { new ->
                    if (new.length > 1) return@BasicTextField
                    if (new.isNotEmpty() && !new.all { it.isDigit() }) return@BasicTextField

                    val mutable = otpValues.toMutableList()
                    mutable[index] = new
                    onOtpChanged(mutable.joinToString(String.empty()))

                    if (new.isNotEmpty() && index < otpLength - 1) {
                        focusRequesters[index + 1].requestFocus()
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShapeM)
                    .border(
                        width = 1.dp,
                        color = if (cellValue.isBlank()) GoshawkGrey else MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShapeM
                    )
                    .focusRequester(focusRequesters[index])
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textStyle = TextRegularS.copy(textAlign = TextAlign.Center),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                )
            )
        }
    }
}
