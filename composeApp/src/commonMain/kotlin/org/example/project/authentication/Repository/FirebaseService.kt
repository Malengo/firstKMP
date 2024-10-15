package org.example.project.authentication.Repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class AuthResponse(val displayName: String? = "", val idToken: String, val email: String, val profilePicture: String)
@Serializable
data class AuthRequest(val email: String, val password: String)
@Serializable
data class UpdateProfileRequest(
    val displayName: String?,
    val idToken: String?,
    val email: String?,
    val photoUrl: String?
)

@Serializable
data class AuthResponseError(
    val error: ErrorInformation
)

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

class FirebaseService {
    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    private val API_KEY = "AIzaSyDuqNTZzIZYszch2V1653Pm19EBU33XRa4"

    suspend fun signUp(email: String, password: String): AuthResponse? {

        val responseBody = httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${API_KEY}") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email, password))
        }

        return if (responseBody.status.value in 200..299) {
            responseBody.body<AuthResponse>()
        } else {
            null
        }
    }

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        val response = httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${API_KEY}") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(email, password))
        }

        return if (response.status.value in 200..299) {
            Result.success(response.body<AuthResponse>())
        } else {
            val responseError = response.body<AuthResponseError>()
            Result.failure(Exception(responseError.error.message))
        }
    }

    suspend fun update(updateProfileRequest: UpdateProfileRequest) {
        val response = httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:update?key=${API_KEY}") {
            contentType(ContentType.Application.Json)
            setBody(updateProfileRequest)
        }

        if (response.status.value in 200..299) {
            println(response.bodyAsText())
        }
    }

    suspend fun uploadImageToFirebase(imageBytes: ByteArray, fileName: String, idToken: String? = null): String? {
        val bucketName = "firstkmp-f9053.appspot.com"
        val filePath = "imagens/${fileName}"
        val firebaseUrl = "https://firebasestorage.googleapis.com/v0/b/$bucketName/o?name=${filePath}"

        return try {
            val response = httpClient.post(firebaseUrl) {
                contentType(ContentType.Image.PNG)
                header("Authorization", "Bearer $idToken")
                setBody(imageBytes)
            }

            if (response.status.value in 200..299) {
                val responseBody = response.body<String>()
                println("Upload bem-sucedido: $responseBody")
                return getImageProfileURL(bucketName, "imagens/", fileName)
            } else {
                println("Falha no upload: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Erro ao fazer upload: ${e.message}")
            null
        }
    }

    fun getImageProfileURL(bucketName: String, filePath: String, image: String): String {
        val encodePath = encodeUrlComponent(filePath)
        return "https://firebasestorage.googleapis.com/v0/b/$bucketName/o/$encodePath$image?alt=media"
    }

    private fun encodeUrlComponent(value: String): String {
        return value.map { char ->
            when (char) {
                in 'A'..'Z', in 'a'..'z', in '0'..'9', '-', '_', '.', '~' -> char.toString()
                ' ' -> "%20"
                else -> "%${char.code.toString(16).uppercase()}"
            }
        }.joinToString("")
    }

}