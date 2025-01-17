package org.example.project.authentication.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.example.project.authentication.model.AuthResponse
import org.example.project.authentication.repository.FirebaseService
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

    suspend fun handlersingUpFireBase(): Result<Profile> {
        return try {
            val authResponse = auth.signUp(_formState.value.email, _formState.value.password)
            authResponse.fold(
                onSuccess = { auth: AuthResponse ->
                    val profile = Profile(
                        auth.displayName,
                        auth.email,
                        auth.idToken,
                        auth.profilePicture
                    )
                    Result.success(profile)
                },
                onFailure = { exception ->
                    Result.failure(Exception(exception.message))
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun handleLoginFirebase(): Result<Profile> {
        return try {
            val response = auth.login(_formState.value.email, _formState.value.password)

            response.fold(
                onSuccess = { auth: AuthResponse ->
                    val profile = Profile(
                        auth.displayName,
                        auth.email,
                        auth.idToken,
                        auth.profilePicture
                    )
                    Result.success(profile)
                },
                onFailure = { exception ->
                    Result.failure(Exception(exception.message))
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}