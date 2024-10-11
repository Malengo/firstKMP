package org.example.project.commonImplementation

import androidx.compose.runtime.Composable

expect class ImagePicker {
    suspend fun pickImage(callback: (Any?) -> Unit)
}

@Composable
expect fun rememberImagePicker(): ImagePicker