package org.example.project.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val displayName: String? = "",
    val idToken: String,
    val email: String,
    val profilePicture:String
)