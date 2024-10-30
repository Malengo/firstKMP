package org.example.project.homeApp

import Colors.ColorsDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import firstkmp.composeapp.generated.resources.Res
import firstkmp.composeapp.generated.resources.backgroundHome
import firstkmp.composeapp.generated.resources.goals
import org.example.project.AppRouterName
import org.example.project.homeApp.components.cardLesson
import org.example.project.sharedViewModel.SharedProfileViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    navToProfileScreen: () -> Unit,
    sharedProfileViewModel: SharedProfileViewModel,
    navController: NavHostController
) {

    val showDialog = remember { mutableStateOf(false) }
    val profileState by sharedProfileViewModel.profile
    val showLoading = remember { mutableStateOf(false) }

    if (showLoading.value) {
        CircularProgressIndicator()
    }

    LaunchedEffect(Unit) {
        showDialog.value = true
        sharedProfileViewModel.profile.value.displayName?.let{
            showDialog.value = false
        }
    }

    if (showDialog.value) {
        showMessage(showDialog, navToProfileScreen)
    }

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(
                    RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
                .paint(
                    painterResource(resource = Res.drawable.backgroundHome),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                modifier = Modifier.padding(top = 80.dp, start = 10.dp)
            ) {
                Text(
                    text = ("Hello, "),
                    fontStyle = FontStyle.Italic,
                    fontSize = 40.sp,
                    color = Color.Black
                )
                Text(
                    text = (sharedProfileViewModel.profile.value.displayName ?: ""),
                    fontSize = 30.sp,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 1.0.em,
                            fontWeight = FontWeight.ExtraLight
                        )
                    ),
                    color = ColorsDefaults.onPrimaryLight,
                    modifier = Modifier.width(220.dp)
                )
            }
            Column (
                modifier = Modifier.padding(top = 50.dp, end = 10.dp)
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data("${profileState.profilePicture}&token=${profileState.idToken}")
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
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(2.dp, color = Color.LightGray),
                            CircleShape
                        )
                        .clickable(
                            enabled = true
                        ) {
                            navToProfileScreen()
                        }
                )
            }
        }
        Column (
            modifier = Modifier
                .padding(top = 30.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier
                    .width(100.dp)
                    .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
            ) {
                Text(
                    "Lessons",
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            ) {
                cardLesson("Writing", Icons.Filled.Edit, Color.Black, ColorsDefaults.secondaryLight) {
                    navController.navigate(
                        AppRouterName.NewWordScreen.name
                    )
                }
                cardLesson("Speaking", Icons.Filled.Face, Color.White, ColorsDefaults.tertiaryLight) {
                    navController.navigate(
                        AppRouterName.NewWordScreen.name
                    )
                }
                cardLesson("Listening", Icons.Filled.PlayArrow, Color.Black, ColorsDefaults.secondaryContainerLight) {
                    navController.navigate(
                        AppRouterName.NewWordScreen.name
                    )
                }
                cardLesson("Game", Icons.Filled.Settings, Color.White, ColorsDefaults.primaryLight) {
                    navController.navigate(
                        AppRouterName.NewWordScreen.name
                    )
                }
            }
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .padding(start = 15.dp, bottom = 30.dp, end = 15.dp, top = 15.dp)
                    .background(Color.LightGray)
            ) {
                Row {
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text(
                            "Basic",
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .background(ColorsDefaults.primaryLight)
                                .width(100.dp),
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        )
                        Text(
                            "Today's \n Challenge",
                            fontSize = 30.sp,
                            style = LocalTextStyle.current.merge(
                                TextStyle(
                                    lineHeight = 1.0.em,
                                    fontWeight = FontWeight.ExtraLight,
                                    textAlign = TextAlign.Center
                                )
                            ),
                            modifier = Modifier.padding(top = 30.dp)
                        )
                        Text(
                            "Discovery name of fruits",
                            color = ColorsDefaults.primaryLight,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp)
                        )
                    }
                }
                Column (
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(Res.drawable.goals),
                        contentDescription = null,
                        modifier = Modifier
                            .height(350.dp)
                            .width(100.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun showMessage(showDialog: MutableState<Boolean>, navToProfileScreen: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            showDialog.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                    navToProfileScreen()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                }
            ) {
                Text("Cancelar")
            }
        },
        text = {
            Text("Atualizar")
        },
        title = {
            Text("Cadastro")
        }
    )
}