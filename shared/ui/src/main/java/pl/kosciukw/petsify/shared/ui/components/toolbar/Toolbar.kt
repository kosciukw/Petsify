package pl.kosciukw.petsify.shared.ui.components.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.ui.components.CircleButton
import pl.kosciukw.petsify.shared.ui.theme.BodyM

@Composable
fun ToolbarCustom(toolbarConfig: ToolbarConfig) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        with(toolbarConfig) {
            if (startIconToolbar != null) {
                CircleButton(
                    imageVector = startIconToolbar,
                    onClick = { onClickStartIconToolbar() }
                )
            } else {
                Spacer(modifier = Modifier.padding(BodyM))
            }

            Text(titleToolbar, style = MaterialTheme.typography.titleLarge)

            if (endIconToolbar != null) {
                CircleButton(
                    imageVector = endIconToolbar,
                    onClick = { onClickEndIconToolbar() }
                )
            } else {
                Spacer(modifier = Modifier.padding(BodyM))
            }
        }
    }
}