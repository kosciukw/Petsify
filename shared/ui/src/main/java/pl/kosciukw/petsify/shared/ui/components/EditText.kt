package pl.kosciukw.petsify.shared.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.TextS
import pl.kosciukw.petsify.shared.ui.theme.TextSecondary
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import pl.kosciukw.petsify.shared.ui.components.shapes.RoundedCornerShapeM
import pl.kosciukw.petsify.shared.utils.empty

@Composable
fun EditText(
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
    TextField(
        modifier = modifier,
        value = text,
        singleLine = true,
        onValueChange = onTextChange,
        shape = RoundedCornerShapeM,
        label = {
            Text(
                style = labelTextStyle,
                text = label
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = inputType
        ),
        textStyle = inputTextStyle,
        trailingIcon = {
            Icon(
                imageVector = trailingIcon,
                contentDescription = iconDescription
            )
        },
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
        },
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun EditTextOutline(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    iconDescription: String = String.empty(),
    labelTextStyle: TextStyle = TextSecondary.copy(
        fontSize = TextS,
        textAlign = TextAlign.Start
    ),
    inputTextStyle: TextStyle = TextRegularS.copy(
        textAlign = TextAlign.Start
    )
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        singleLine = true,
        onValueChange = onTextChange,
        shape = RoundedCornerShapeM,
        label = {
            Text(
                style = labelTextStyle,
                text = label
            )
        },
        textStyle = inputTextStyle,
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription
            )
        }
    )
}
