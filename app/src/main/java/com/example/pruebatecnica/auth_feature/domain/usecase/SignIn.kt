package com.example.pruebatecnica.auth_feature.domain.usecase

import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class SignIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(credentials:AuthCredentials):ResponseState<Unit> {
        if (credentials.email.isBlank()&&credentials.password.isBlank()&&credentials.fcmToken==null){
            return ResponseState.Error("Invalid credentials",401)
        }
        return withTimeoutOrNull(5000) {
            authRepository.signIn(credentials = credentials)
        } ?: ResponseState.Error("Request timed out", 408)
    }
}