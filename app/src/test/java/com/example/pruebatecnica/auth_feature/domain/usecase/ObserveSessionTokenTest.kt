package com.example.pruebatecnica.auth_feature.domain.usecase

import com.example.pruebatecnica.auth_feature.domain.model.AuthSession
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ObserveSessionTokenTest{
    @RelaxedMockK
    private lateinit var authRepository: AuthRepository
    private lateinit var observeSessionToken:ObserveSessionToken

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        observeSessionToken= ObserveSessionToken(authRepository)
    }

    @Test
    fun `should return the session token flow`() = runBlocking {
        val expectedSession = AuthSession(email = "test@example.com", fingerprint = 0)

        coEvery { authRepository.token } returns flowOf(expectedSession)

        val result = observeSessionToken()

        result.collect { authSession ->
            assertEquals(expectedSession.email, authSession?.email)
            assertEquals(expectedSession.fingerprint, authSession?.fingerprint)
        }
    }
}