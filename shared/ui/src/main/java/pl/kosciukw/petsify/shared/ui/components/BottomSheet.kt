package pl.kosciukw.petsify.shared.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialBottomSheetScreen(
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
    draggable: Boolean,
    sheetRatio: Float = 0.3f
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = true,
        confirmValueChange = { draggable }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeight = screenHeight * sheetRatio

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = { sheetContent() },
        sheetPeekHeight = sheetHeight,
        sheetDragHandle = if (draggable) {
            { BottomSheetDefaults.DragHandle() }
        } else null,
        sheetSwipeEnabled = draggable,
        sheetShape = RoundedCornerShape(
            topStart = 42.dp,
            topEnd = 42.dp
        ), contentColor = Color.White,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    )
}