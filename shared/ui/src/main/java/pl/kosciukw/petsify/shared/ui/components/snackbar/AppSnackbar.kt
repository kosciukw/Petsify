package pl.kosciukw.petsify.shared.ui.components.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.kosciukw.petsify.shared.ui.theme.BodyM
import pl.kosciukw.petsify.shared.ui.R as SharedR

@Composable
fun AppSnackbar(
    modifier: Modifier,
    isVisible: Boolean,
    title: String,
    onDismiss: () -> Unit
) {
    if (isVisible) {
        Snackbar(
            modifier = modifier
                .padding(BodyM),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            action = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(SharedR.string.ok_button),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}