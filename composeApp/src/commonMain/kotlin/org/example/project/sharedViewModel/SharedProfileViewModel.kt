package org.example.project.sharedViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.project.authentication.model.UpdateProfileRequest
import org.example.project.authentication.repository.FirebaseService
import org.example.project.profile.Profile

class SharedProfileViewModel: ViewModel() {

    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile
    private val _firebaseService = FirebaseService()

    fun addProfile(newProfile: Profile) {
        _profile.value = newProfile
    }

    fun onEmailChanged(newEmail: String) {
        _profile.value = _profile.value.copy(email = newEmail)
    }

    fun onDisplayNameChanged(newName: String) {
        _profile.value = _profile.value.copy(displayName = newName)
    }

    suspend fun onProfilePictureChanged(newImageUrl: ByteArray) {
        val url = _firebaseService.uploadImageToFirebase(newImageUrl, "${profile.value.email}_profilepicture.png", profile.value.idToken)
        _profile.value = _profile.value.copy(profilePicture = url)
    }

    suspend fun handlerUpdateProfileFireBase() {
        val updateProfileRequest = UpdateProfileRequest(
            profile.value.displayName,
            profile.value.idToken,
            profile.value.email,
            profile.value.profilePicture
        )
        _firebaseService.update(updateProfileRequest)
    }

}