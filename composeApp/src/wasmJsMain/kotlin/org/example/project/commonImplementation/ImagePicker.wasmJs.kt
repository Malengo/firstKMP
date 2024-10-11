package org.example.project.commonImplementation

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import kotlinx.serialization.Serializable
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

actual class ImagePicker {
    @OptIn(ExperimentalEncodingApi::class)
    actual suspend fun pickImage(callback: (Any?) -> Unit) {

        return suspendCoroutine { continuation ->
            val input = document.createElement("input") as HTMLInputElement
            input.type = "file"
            input.accept = "image/*"

            input.onchange = {
                val file = input.files?.item(0)
                if (file != null) {
                    val reader = FileReader()
                    reader.onload = {
                        val imageBase64 = reader.result.toString().substringAfter("base64,")
                        val destination = ByteArray(Base64.decode(imageBase64).size)
                        val byteArrayImage = Base64.decodeIntoByteArray(imageBase64, destination,0)
                        continuation.resume(callback(destination.copyOf(byteArrayImage)))
                    }
                    console(file)
                    reader.readAsDataURL(file)
                } else {
                    continuation.resume(callback(null))
                }
            }

            input.click()
        }
    }
}

fun console(result: JsAny?) {
    js("console.log(result)")
}

@Composable
actual fun rememberImagePicker(): ImagePicker {
    return ImagePicker()
}