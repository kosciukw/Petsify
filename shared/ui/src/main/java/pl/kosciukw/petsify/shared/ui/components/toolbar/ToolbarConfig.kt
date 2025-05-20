package pl.kosciukw.petsify.shared.ui.components.toolbar

import androidx.compose.ui.graphics.vector.ImageVector
import pl.kosciukw.petsify.shared.utils.empty

data class ToolbarConfig(
    val startIconToolbar: ImageVector?,
    val onClickStartIconToolbar: () -> Unit,
    val titleToolbar: String,
    val endIconToolbar: ImageVector?,
    val onClickEndIconToolbar: () -> Unit
) {
    override fun toString() = String.empty()
}