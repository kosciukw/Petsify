package pl.kosciukw.petsify.shared.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun BackgroundImage(
    modifier: Modifier,
    imageId: Int
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = imageId),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}