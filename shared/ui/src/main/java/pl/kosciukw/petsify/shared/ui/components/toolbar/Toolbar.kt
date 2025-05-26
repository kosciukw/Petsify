package pl.kosciukw.petsify.shared.ui.components.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.kosciukw.petsify.shared.ui.components.CircleButton
import pl.kosciukw.petsify.shared.ui.theme.BodyM
import pl.kosciukw.petsify.shared.ui.theme.paddingGapM
import pl.kosciukw.petsify.shared.utils.empty

@Composable
fun ToolbarCustom(
    toolbarConfig: ToolbarConfig
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingGapM),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        with(toolbarConfig) {
            if (startIconToolbar != null) {
                IconButton(
                    onClick = { onClickStartIconToolbar() }
                ) {
                    Icon(
                        imageVector = startIconToolbar,
                        contentDescription = String.empty() // TODO
                    )
                }
            } else Spacer(modifier = Modifier.padding(BodyM))

            Text(
                text = titleToolbar,
                style = MaterialTheme.typography.titleLarge
            )

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