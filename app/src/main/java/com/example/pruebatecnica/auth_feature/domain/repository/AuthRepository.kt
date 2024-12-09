package com.example.pruebatecnica.auth_feature.domain.repository

import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.core_feature.data.model.ResponseState

interface AuthRepository {
    suspend fun signIn(credentials:AuthCredentials):ResponseState<Unit>
    suspend fun signUp(credentials: AuthCredentials)
}