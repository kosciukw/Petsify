package pl.kosciukw.petsify.shared.ui.components.toolbar

import androidx.compose.ui.graphics.vector.ImageVector
import pl.kosciukw.petsify.shared.utils.empty

data class ToolbarConfig(
    val titleToolbar: String,
    val startIconToolbar: ImageVector? = null,
    val onClickStartIconToolbar: () -> Unit = {},
    val endIconToolbar: ImageVector? = null,
    val onClickEndIconToolbar: () -> Unit = {}
) {
    override fun toString() = String.empty()
}