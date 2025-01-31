package nz.co.example.app.ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
internal fun Image(
    modifier: Modifier = Modifier, imageUrl: String
) {
    val request = with(ImageRequest.Builder(LocalContext.current)) {
        data(imageUrl)

        crossfade(true)
        build()
    }

    AsyncImage(
        modifier = modifier, model = request, contentDescription = null
    )
}