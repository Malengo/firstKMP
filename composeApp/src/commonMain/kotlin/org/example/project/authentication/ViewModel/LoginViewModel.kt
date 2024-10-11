package org.example.project.authentication.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.example.project.authentication.Repository.FirebaseService
import org.example.project.profile.Profile

data class LoginForm(val email: String = "", val password: String = "", val isLogin: Boolean = true)

class LoginViewModel : ViewModel() {

    private val _formState = mutableStateOf(LoginForm())
    val formState: State<LoginForm> = _formState
    private val auth = FirebaseService()

    fun onEmailChanged(newEmail: String) {
        _formState.value = _formState.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        _formState.value = _formState.value.copy(password = newPassword)
    }

    fun onTypeLoginChange(newChoise: Boolean) {
        _formState.value = _formState.value.copy(isLogin = newChoise)
    }

    suspend fun handlersingUpFireBase(): Profile? {
        val authResponse = auth.signUp("jose.malengo@hotmail.com", "123456")
        if (authResponse != null) {
            val profile = Profile(
                authResponse.displayName,
                authResponse.email,
                authResponse.idToken,
                authResponse.profilePicture
            )
            return profile
        }
        return null
    }

    suspend fun handleLoginFirebase(): Profile? {
        val authResponse = auth.login("jose.malengo@hotmail.com", "123456")
        if (authResponse != null) {
            val profile = Profile(
                authResponse.displayName,
                authResponse.email,
                authResponse.idToken,
                authResponse.profilePicture
            )
            return profile
        }
        return null
    }
}