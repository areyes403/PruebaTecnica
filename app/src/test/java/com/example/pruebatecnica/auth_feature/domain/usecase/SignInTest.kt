package com.example.pruebatecnica.auth_feature.domain.usecase

import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.math.exp
import kotlin.math.sign

class SignInTest{
    @RelaxedMockK
    private lateinit var authRepository: AuthRepository
    private lateinit var signIn: SignIn

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        signIn= SignIn(authRepository)
    }

    @Test
    fun `when user is logging with email and password it should return Success`() = runBlocking {
        val credentials = AuthCredentials(
            email = "user@example.com",
            password = "password123",
            fcmToken = null
        )
        val expectedData = Unit

        coEvery { authRepository.signIn(any()) } returns ResponseState.Success(Unit)

        val result = signIn(credentials)

        coVerify(exactly = 1) { authRepository.signIn(credentials) }
        assertTrue(result is ResponseState.Success)
        val successResul = result as ResponseState.Success
        assert(successResul.data == expectedData)

    }

    @Test
    fun `when user provides invalid credentials it should return Error`() = runBlocking {
        // Preparar las credenciales de ejemplo
        val credentials = AuthCredentials(
            email = "user@example.com",
            password = "",
            fcmToken = null
        )

        val expectedCode=401

        coEvery { authRepository.signIn(any()) } returns ResponseState.Error("Not valid credentials",401)

        val result = signIn(credentials)

        coVerify(exactly = 1) { authRepository.signIn(credentials) }
        assertTrue(result is ResponseState.Error)
        val errorResult= result as  ResponseState.Error
        assert(errorResult.code==expectedCode)

    }

    @Test
    fun `when a timeout occurs it should return Error`() = runBlocking {
        // Preparar las credenciales de ejemplo
        val credentials = AuthCredentials(
            email = "user@example.com",
            password = "123456",
            fcmToken = null
        )

        val expectedCode=408

        coEvery { authRepository.signIn(any()) } returns ResponseState.Error("Request timed out",408)

        val result = withTimeoutOrNull(5000) {
            signIn(credentials)
        } ?: ResponseState.Error("Request timed out", 408)

        coVerify(exactly = 1) { authRepository.signIn(credentials) }
        assertTrue(result is ResponseState.Error)
        val errorResult= result as  ResponseState.Error
        assert(errorResult.code==expectedCode)

    }
}