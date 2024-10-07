package com.example.devicesmanager.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.devicesmanager.R
import com.example.devicesmanager.helpers.PreviewWrapper

@Composable
fun StubScreen() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        imageLoader = imageLoader,
        model = R.drawable.monkey_swimming,
        contentDescription = null
    )
}

@Preview
@Composable
private fun Preview() {
    PreviewWrapper {
        StubScreen()
    }
}