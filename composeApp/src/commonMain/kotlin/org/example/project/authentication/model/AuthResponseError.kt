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

enum class ErrorResquestEnum(val code: Int, val message: String) {
    INVALID_CREDENTIALS(400, "Login ou Senha inválidos");

    companion object {
        fun fromCode(code: Int) = entries.find { it.code == code }
    }
}