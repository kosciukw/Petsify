package pl.kosciukw.petsify.shared.ui.components.secure

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.TextS
import pl.kosciukw.petsify.shared.ui.theme.TextSecondary
import pl.kosciukw.petsify.shared.utils.empty

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    trailingIcon: ImageVector,
    iconDescription: String = String.empty(),
    labelTextStyle: TextStyle = TextSecondary.copy(
        fontSize = TextS,
        textAlign = TextAlign.Start
    ),
    inputType: KeyboardType = KeyboardType.Text,
    inputTextStyle: TextStyle = TextRegularS.copy(textAlign = TextAlign.Start),
    errorMessage: String? = null,
    isErrorMessageEnabled: Boolean = false,
) {
    val coroutineScope = rememberCoroutineScope()
    var visibleIndex by remember { mutableStateOf<Int?>(null) }
    var previousText by remember { mutableStateOf(text) }
    var revealJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(text) {
        if (inputType == KeyboardType.Password && text.length != previousText.length) {
            val newCharIndex = text.length - 1
            revealJob?.cancel()
            visibleIndex = newCharIndex
            previousText = text
            revealJob = coroutineScope.launch {
                delay(1000)
                visibleIndex = null
            }
        }
    }

    val visualTransformation = when (inputType) {
        KeyboardType.Password -> OneCharRevealTransformation(visibleIndex)
        else -> VisualTransformation.None
    }

    TextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        label = { Text(style = labelTextStyle, text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = inputType),
        textStyle = inputTextStyle,
        trailingIcon = {
            Icon(imageVector = trailingIcon, contentDescription = iconDescription)
        },
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.error,
        ),
        isError = isErrorMessageEnabled,
        supportingText = {
            if (isErrorMessageEnabled && errorMessage != null) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = TextS
                    )
                )
            }
        }
    )
}

private class OneCharRevealTransformation(
    private val visibleIndex: Int?
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val transformed = buildString {
            text.forEachIndexed { index, char ->
                append(if (index == visibleIndex) char else MASKING_CHAR)
            }
        }
        return TransformedText(AnnotatedString(transformed), OffsetMapping.Identity)
    }

    companion object {
        private const val MASKING_CHAR = '•'
    }
}