package org.example.project.authentication.repository

import com.google.firebase.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.authentication.model.AuthRequest
import org.example.project.authentication.model.AuthResponse
import org.example.project.authentication.model.AuthResponseError
import org.example.project.authentication.model.ErrorResquestEnum
import org.example.project.authentication.model.UpdateProfileRequest

class FirebaseService {
    val db = Firebase.firestore
    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    private val API_KEY = "AIzaSyD5awyMlXDuMEsVAFANqIk8du6Hj_OYuf4"

    suspend fun signUp(email: String, password: String): Result<AuthResponse> {

        val responseBody =
            httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${API_KEY}") {
                contentType(ContentType.Application.Json)
                setBody(AuthRequest(email, password))
            }

        return if (responseBody.status.value in 200..299) {
            Result.success(responseBody.body<AuthResponse>())
        } else {
            val responseError = responseBody.body<AuthResponseError>()
            val erroMessage: ErrorResquestEnum? = ErrorResquestEnum.fromCode(responseError.error.code)
            Result.failure(Exception(erroMessage?.message))
        }
    }

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        val response =
            httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${API_KEY}&returnSecureToken=true") {
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
        val bucketName = "englishapp-8fd93.firebasestorage.app"
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

    suspend fun getAllLessons(idToken: String) {
        val url = "https://firestore.googleapis.com/v1/projects/englishapp-8fd93/databases/(default)/documents/lessons/"

        try {
            val response = httpClient.get(url) {
                header(HttpHeaders.Authorization, "Bearer $idToken")
            }
            println("Resposta: ${response.bodyAsText()}")
        } catch (e: ClientRequestException) {
            println("Erro de Cliente: ${e.response.status}")
            println("Mensagem: ${e.response.bodyAsText()}")
        } catch (e: Exception) {
            println("Erro Geral: ${e.message}")
        }
    }

}