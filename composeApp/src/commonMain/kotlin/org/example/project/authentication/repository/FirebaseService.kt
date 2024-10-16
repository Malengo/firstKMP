package org.example.project.authentication.repository

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
import kotlinx.serialization.json.Json
import org.example.project.authentication.model.AuthRequest
import org.example.project.authentication.model.AuthResponse
import org.example.project.authentication.model.AuthResponseError
import org.example.project.authentication.model.ErrorResquestEnum
import org.example.project.authentication.model.UpdateProfileRequest

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

        val responseBody =
            httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${API_KEY}") {
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
        val response =
            httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${API_KEY}") {
                contentType(ContentType.Application.Json)
                setBody(AuthRequest(email, password))
            }

        return if (response.status.value in 200..299) {
            Result.success(response.body<AuthResponse>())
        } else {
            val responseError = response.body<AuthResponseError>()
            val erroMessage: ErrorResquestEnum? = ErrorResquestEnum.fromCode(responseError.error.code)
            Result.failure(Exception(erroMessage?.message))
        }
    }

    suspend fun update(updateProfileRequest: UpdateProfileRequest) {
        val response =
            httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:update?key=${API_KEY}") {
                contentType(ContentType.Application.Json)
                setBody(updateProfileRequest)
            }

        if (response.status.value in 200..299) {
            println(response.bodyAsText())
        }
    }

    suspend fun uploadImageToFirebase(
        imageBytes: ByteArray,
        fileName: String,
        idToken: String? = null
    ): String? {
        val bucketName = "firstkmp-f9053.appspot.com"
        val filePath = "imagens/${fileName}"
        val firebaseUrl =
            "https://firebasestorage.googleapis.com/v0/b/$bucketName/o?name=${filePath}"

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