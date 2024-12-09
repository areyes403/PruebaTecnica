package com.example.pruebatecnica.auth_feature.data.repository

import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl (
    private val firebaseAuth:FirebaseAuth
):AuthRepository {
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
}