package com.example.pruebatecnica.auth_feature.domain.usecase

import com.example.pruebatecnica.auth_feature.data.model.RegisterUser
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignUpTest{
    @RelaxedMockK
    private lateinit var authRepository: AuthRepository
    private lateinit var signUp:SignUp

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        signUp= SignUp(authRepository)
    }

    @Test
    fun `should return Error if email is invalid`() = runBlocking {
        val invalidEmailCredentials = RegisterUser(email = "invalid-email", pass = "ValidPass1")

        val expectedResponse=ResponseState.Error("Invalid credentials format",null)

        val result = signUp(invalidEmailCredentials)

        assertTrue(result is ResponseState.Error)
        val errorResult=result as ResponseState.Error
        assert(errorResult.msg == expectedResponse.msg)
    }

    @Test
    fun `should return successful response when credentials are valid`() = runBlocking {
        val validCredentials = RegisterUser(email = "valid@example.com", pass = "ValidPass1-")
        val expectedResult=ResponseState.Success(Unit)

        coEvery { authRepository.signUp(any()) } returns ResponseState.Success(Unit)

        val result = signUp(validCredentials)

        coVerify (exactly = 1) { authRepository.signUp(validCredentials) }
        assertTrue(result is ResponseState.Success)
        val successResult= result as ResponseState.Success
        assertEquals(expectedResult,successResult)
    }
}