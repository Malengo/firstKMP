package org.example.project.profile

data class Profile(
    var displayName: String? = "",
    var email: String = "",
    val idToken: String = "",
    var profilePicture: String? = ""
) {
}