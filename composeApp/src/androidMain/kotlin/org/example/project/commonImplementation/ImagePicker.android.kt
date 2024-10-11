package org.example.project.commonImplementation

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

actual class ImagePicker(
    private val launcher: (callback: (Any?) -> Unit) -> Unit
) {
    actual suspend fun pickImage(callback: (Any?) -> Unit) {
        launcher(callback)
    }
}

@Composable
actual fun rememberImagePicker(): ImagePicker {
    var callback by remember { mutableStateOf<(Any?) -> Unit>({}) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            val byteArray = uriToByteArray(context, uri)
            callback(byteArray)
        } else {
            callback(null)
        }
    }

    return remember {
        ImagePicker { cb ->
            callback = cb
            launcher.launch("image/*")
        }
    }
}

fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use {
            val buffer = ByteArrayOutputStream()
            val data = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(data).also { bytesRead = it } != -1) {
                buffer.write(data, 0, bytesRead)
            }
            buffer.toByteArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}