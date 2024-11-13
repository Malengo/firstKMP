package org.example.project.profile

import Colors.ColorsDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.commonImplementation.rememberImagePicker
import org.example.project.components.DialogError
import org.example.project.sharedViewModel.SharedProfileViewModel

@Composable
fun ProfileScreen(sharedProfileViewModel: SharedProfileViewModel) {
    var selectedImage by remember { mutableStateOf<Any?>(null) }
    val imagePicker = rememberImagePicker()
    val scope = rememberCoroutineScope()
    val profileState by sharedProfileViewModel.profile
    val openDialog = remember { mutableStateOf(false) }
    var messageError by remember { mutableStateOf("") }

    if (openDialog.value) {
        DialogError(messageError, openDialog)
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight().padding(top = 50.dp)
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(
                        selectedImage
                            ?: "${profileState.profilePicture}&token=${profileState.idToken}"
                    )
                    .memoryCachePolicy(CachePolicy.DISABLED)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        strokeWidth = 8.dp,
                        color = ColorsDefaults.primaryLight
                    )
                },
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(4.dp, color = Color.LightGray),
                        CircleShape
                    )
            )
            FloatingActionButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {
                    scope.launch {
                        imagePicker.pickImage { imageBitmap ->
                            if (imageBitmap != null) {
                                scope.launch(Dispatchers.Main) {
                                    sharedProfileViewModel.onProfilePictureChanged(imageBitmap as ByteArray)
                                    selectedImage = imageBitmap
                                }
                            } else {
                                openDialog.value = true
                                messageError = "Sem permissão de acesso a galeria"
                            }
                        }
                    }
                }) {
                Icon(Icons.Filled.Person, "")
            }
        }
        TextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            value = profileState.displayName ?: "",
            onValueChange = sharedProfileViewModel::onDisplayNameChanged,
            label = {
                Text("Nome")
            }
        )
        TextField(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            value = profileState.email,
            onValueChange = sharedProfileViewModel::onEmailChanged,
            label = {
                Text("Email")
            }
        )
        Button(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(ColorsDefaults.primaryLight),
            onClick = {
                scope.launch {
                    sharedProfileViewModel.handlerUpdateProfileFireBase()
                }
            }
        ) {
            Text(
                color = ColorsDefaults.onPrimaryLight,
                text = "Atualizar Perfil"
            )
        }
    }
}
