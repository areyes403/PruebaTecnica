package com.example.pruebatecnica.auth_feature.domain.usecase

import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import javax.inject.Inject

class SignIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(credentials:AuthCredentials)=authRepository.signIn(credentials = credentials)
}