package pl.kosciukw.petsify.shared.ui.components.selectable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey

@Composable
fun SelectablePill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    selectedContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    unselectedContainerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    selectedTextColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    unselectedTextColor: Color = GoshawkGrey
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(999.dp),
        color = if (selected) selectedContainerColor else unselectedContainerColor
    ) {
        Box(
            modifier = Modifier.padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = textStyle,
                color = if (selected) selectedTextColor else unselectedTextColor
            )
        }
    }
}
