package org.example.project.authentication.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseError(val error: ErrorInformation)
@Serializable
data class ErrorInformation(
    val code: Int,
    val message: String,
    val errors: List<ErrorDetail>
)
@Serializable
data class ErrorDetail(
    val message: String,
    val domain: String,
    val reason: String
)