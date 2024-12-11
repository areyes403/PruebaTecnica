package com.example.pruebatecnica.auth_feature.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

private val Context.sessionStore by preferencesDataStore(name = "token_session")

class AuthRepositoryImpl (
    private val firebaseAuth:FirebaseAuth,
    private val context: Context
):AuthRepository {

    private val sessionStore = context.sessionStore
    private val SESSION_KEY = stringPreferencesKey("session")


    override suspend fun signIn(credentials: AuthCredentials): ResponseState<Unit> = try {
        firebaseAuth.signInWithEmailAndPassword(credentials.email,credentials.password).await()
        ResponseState.Success(Unit)
    }catch (e:FirebaseAuthInvalidCredentialsException){
        ResponseState.Error("Not valid credentials",401 )
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

    override val token: Flow<String?> = sessionStore.data
    .map { preferences->
        preferences[SESSION_KEY]
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
}