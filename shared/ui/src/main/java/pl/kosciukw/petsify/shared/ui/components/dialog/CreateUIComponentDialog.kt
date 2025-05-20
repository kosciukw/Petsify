package pl.kosciukw.petsify.shared.ui.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CreateUIComponentDialog(
    title: String,
    description: String,
    onRemoveHeadFromQueue: () -> Unit
) {

    GenericDialog(
        modifier = Modifier.fillMaxWidth(0.9f),
        title = title,
        description = description,
        onRemoveHeadFromQueue = onRemoveHeadFromQueue
    )
}

@Composable
private fun GenericDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onRemoveHeadFromQueue: () -> Unit,
) {
    CustomAlertDialog(
        onDismissRequest = {
            onRemoveHeadFromQueue()
        },
        modifier = modifier
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = title)
                Text(text = description)
            }
        }
    }
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Card(
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onBackground
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}