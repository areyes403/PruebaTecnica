package com.example.pruebatecnica.auth_feature.data.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.auth_feature.domain.model.AuthSession
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.example.pruebatecnica.auth_feature.security.CryptoManager
import com.example.pruebatecnica.auth_feature.security.JwtUtil.generateToken
import com.example.pruebatecnica.auth_feature.security.JwtUtil.getAuthSession
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.SecureRandom
import java.util.Base64

private val Context.sessionStore by preferencesDataStore(name = "token_session")

class AuthRepositoryImpl (
    private val firebaseAuth:FirebaseAuth,
    private val context: Context
):AuthRepository {

    private val sessionStore = context.sessionStore
    private val SESSION_KEY = stringPreferencesKey("session")
    private val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    private val cryptoManager = CryptoManager()

    override suspend fun signIn(credentials: AuthCredentials): ResponseState<Unit> = try {
        if (credentials.email.isBlank()&&credentials.password.isBlank()&&credentials.fcmToken==null){
            throw IllegalArgumentException("Invalid credentials")
        }
        withTimeoutOrNull(5000) {
            if (credentials.fcmToken==null){
                val response = firebaseAuth
                    .signInWithEmailAndPassword(credentials.email, credentials.password)
                    .await()
                val claims = mapOf(
                    "email" to response.user!!.email!!,
                    "fingerprint" to credentials.fingerprint.toString(),
                )
                val newToken = generateToken(claims = claims, username = credentials.email, getPrivateKey())
                saveToken(newToken)
            }else{
                val response = firebaseAuth
                    .signInWithCredential(GoogleAuthProvider.getCredential(credentials.fcmToken, null))
                    .await()
                val claims = mapOf(
                    "email" to response.user!!.email!!,
                    "fingerprint" to "1"
                )
                val newToken = generateToken(claims = claims, username = credentials.email, getPrivateKey())
                saveToken(newToken)
            }
            ResponseState.Success(Unit)
        } ?: ResponseState.Error("Request timed out", 408)
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        ResponseState.Error("Not valid credentials", 401)
    } catch (e: IllegalArgumentException) {
        ResponseState.Error(e.message, 401)
    } catch (e: Exception) {
        ResponseState.Error(e.message, 500)
    }

    override suspend fun signUp(credentials: AuthCredentials) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(credentials.email,credentials.password).await()
        }catch (e: FirebaseAuthUserCollisionException){
            e.printStackTrace()
        }
        catch (e: FirebaseAuthInvalidCredentialsException){
            e.printStackTrace()
        }
    }

    override val token: Flow<AuthSession?> = sessionStore.data
    .map { preferences->
        var authState:AuthSession?=null
        preferences[SESSION_KEY]?.let {
            val data=getAuthSession(it,getPrivateKey())
            data?.let { session->
                authState= AuthSession(email = session.email,session.fingerprint)
            }
        }
        authState
    }

    override suspend fun saveToken(token: String) {
        sessionStore.edit { preferences ->
            preferences[SESSION_KEY] = token
        }
    }

    override suspend fun signOut() {
        sessionStore.edit { preferences ->
            preferences.remove(SESSION_KEY)
        }
    }

    private fun getPrivateKey(): String {
        val file = File(context.filesDir, "secret.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        FileInputStream(file).use { fis ->
            val secretKey = sharedPreferences.getString("SECRET_KEY", null)
            if (secretKey == null) {
                val generatedKey = generateSecretKey()
                val keyBytes = generatedKey.encodeToByteArray()
                FileOutputStream(file).use { fos ->
                    val encryptedKey = cryptoManager.encrypt(bytes = keyBytes, outputStream = fos)
                    sharedPreferences.edit().putString("SECRET_KEY", encryptedKey.toString()).apply()
                }
                return cryptoManager.decrypt(inputStream = fis).decodeToString()
            } else {
                // Si la clave ya existe en SharedPreferences, simplemente la desciframos
                return cryptoManager.decrypt(inputStream = fis).decodeToString()
            }
        }
    }

    private fun generateSecretKey(): String {
        val keyLength = 32
        val random = SecureRandom()

        val keyBytes = ByteArray(keyLength)
        random.nextBytes(keyBytes)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(keyBytes)
        } else {
            android.util.Base64.encodeToString(keyBytes, android.util.Base64.NO_WRAP)
        }
    }
}