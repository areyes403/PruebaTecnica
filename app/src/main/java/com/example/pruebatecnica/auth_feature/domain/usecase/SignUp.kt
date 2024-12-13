package com.example.pruebatecnica.auth_feature.domain.usecase

import com.example.pruebatecnica.auth_feature.data.model.RegisterUser
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.util.Utils
import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidEmail
import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidPassword
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class SignUp @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(credentials: RegisterUser):ResponseState<Unit>{
        val validEmail=isValidEmail(credentials.email)
        val validPassword= isValidPassword(credentials.pass)
        if (!validEmail || !validPassword){
            return ResponseState.Error("Invalid credentials format",null)
        }
        return withTimeoutOrNull(5000){
            authRepository.signUp(credentials = credentials)
        } ?: ResponseState.Error("Request timed out", 408)
    }
}