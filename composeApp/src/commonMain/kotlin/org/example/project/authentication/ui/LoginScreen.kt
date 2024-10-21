package org.example.project.authentication.ui

import Colors.ColorsDefaults
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import firstkmp.composeapp.generated.resources.Res
import firstkmp.composeapp.generated.resources.englishApp
import kotlinx.coroutines.launch
import org.example.project.authentication.viewModel.LoginViewModel
import org.example.project.sharedViewModel.SharedProfileViewModel
import org.jetbrains.compose.resources.painterResource


@Composable
fun LoginScreen(
    onNavigateToHomeScreen: () -> Unit,
    sharedProfileViewModel: SharedProfileViewModel,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel { LoginViewModel() }
)  {
    val formState by viewModel.formState
    val scope = rememberCoroutineScope()
    var buttonLoginFormText by remember { mutableStateOf("Login") }
    var textChoiseForm by remember { mutableStateOf("Criar uma nova conta") }
    val openDialog = remember { mutableStateOf(false) }
    var messageError by remember { mutableStateOf("") }

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(ColorsDefaults.errorContainerLight.copy(alpha = 0.8f))
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "",
                    tint = ColorsDefaults.onPrimaryLight,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    messageError,
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Black
                )
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.englishApp),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextField(
            modifier = Modifier.padding(all = 10.dp).fillMaxWidth(),
            value = formState.email,
            onValueChange =  viewModel::onEmailChanged ,
            label = {
                Text("Email:")
            }
        )
        TextField(
            modifier = Modifier.padding(all = 10.dp).fillMaxWidth(),
            value = formState.password,
            onValueChange = viewModel::onPasswordChanged,
            label = {
                Text("Senha")
            }
        )
        Button(
            modifier = Modifier.padding(all = 10.dp).fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(ColorsDefaults.primaryLight),
            onClick = {
                scope.launch {
                    //val response = if (formState.isLogin) viewModel.handleLoginFirebase() else viewModel.handlersingUpFireBase()
                    val response = viewModel.handleLoginFirebase()
                    response.onSuccess { profile ->
                        sharedProfileViewModel.addProfile(profile)
                        onNavigateToHomeScreen()
                    }.onFailure { message ->
                        messageError = message.message.toString()
                        openDialog.value = true
                    }
                }
            }
        ) {
            Text(buttonLoginFormText, color = ColorsDefaults.onPrimaryLight)
        }
        ClickableText(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 10.dp),
            text = AnnotatedString(textChoiseForm),
            style = TextStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ),
            onClick = {
                viewModel.onTypeLoginChange(!formState.isLogin)
                if (formState.isLogin) {
                    textChoiseForm = "Criar uma nova conta"
                    buttonLoginFormText = "Login"
                } else {
                    textChoiseForm = "Logar com uma conta existente"
                    buttonLoginFormText = "Criar nova conta"
                }
            }
        )
    }
}