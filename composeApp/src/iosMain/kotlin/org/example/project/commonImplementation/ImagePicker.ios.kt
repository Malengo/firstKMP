package org.example.project.commonImplementation

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject

actual class ImagePicker {
    @OptIn(DelicateCoroutinesApi::class)
    actual suspend fun pickImage(callback: (Any?) -> Unit) {
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        PHPhotoLibrary.requestAuthorization { staus ->
            if (staus == PHAuthorizationStatusAuthorized) {
                val imagePicker = UIImagePickerController()
                val galleryDelegate = object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
                    override fun imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>) {
                        val image = didFinishPickingMediaWithInfo[UIImagePickerControllerEditedImage] as? UIImage
                            ?: didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                        picker.dismissViewControllerAnimated(true, null)
                        callback(toByteArray(image))
                    }
                }
                imagePicker.setDelegate(galleryDelegate)
                imagePicker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary)
                imagePicker.setAllowsEditing(true)
                imagePicker.setDelegate(galleryDelegate)
                GlobalScope.launch(Dispatchers.Main) {
                    viewController?.presentViewController(imagePicker, true, null)
                }
            } else {
                callback(null)
            }
        }
    }
}
@OptIn(ExperimentalForeignApi::class)
private fun toByteArray(image: UIImage?): ByteArray? {
    return image?.let {
        val imageData = UIImageJPEGRepresentation(it, 0.99)
        val bytes = imageData?.bytes?.reinterpret<ByteVar>()
        val length = imageData?.length?.toInt() ?: 0
        ByteArray(length) { index -> bytes?.get(index)?.toByte() ?:0 }
    }
}
@Composable
actual fun rememberImagePicker(): ImagePicker {
    return ImagePicker()
}