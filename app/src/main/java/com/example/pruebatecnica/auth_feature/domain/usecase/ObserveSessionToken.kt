package com.example.pruebatecnica.auth_feature.domain.usecase

import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import javax.inject.Inject

class ObserveSessionToken @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke()=authRepository.token
}