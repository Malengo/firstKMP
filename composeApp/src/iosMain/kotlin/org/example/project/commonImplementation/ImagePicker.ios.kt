package org.example.project.commonImplementation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

 class ImagePicker {
    actual suspend fun pickImage(callback: (ImageBitmap?) -> Unit) {
    }
}

@Composable
 fun rememberImagePicker(): ImagePicker {
    TODO("Not yet implemented")
}