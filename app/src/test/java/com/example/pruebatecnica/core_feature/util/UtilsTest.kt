package com.example.pruebatecnica.core_feature.util

import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidEmail
import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidPassword
import org.junit.Assert.*
import org.junit.Test

class UtilsTest{

    @Test
    fun `should return true for valid email`() {
        val email = "usuario@dominio.com"
        val result = isValidEmail(email)
        assertTrue(result)
    }
    @Test
    fun `should return false for invalid email`() {
        val email = "usuario@dominiocom"
        val result = isValidEmail(email)
        assertFalse(result)
    }

    @Test
    fun `should return true for valid password`() {
        val password = "Valid1Password!"
        val result = isValidPassword(password)
        assertTrue(result)
    }

    @Test
    fun `should return false if password is too short`() {
        val password = "Short1!"
        val result = isValidPassword(password)
        assertFalse(result)
    }

    @Test
    fun `should return false if password has no digits`() {
        val password = "NoDigitsHere!"
        val result = isValidPassword(password)
        assertFalse(result)
    }

    @Test
    fun `should return false if password has no uppercase letter`() {
        val password = "lowercase1!"
        val result = isValidPassword(password)
        assertFalse(result)
    }

    @Test
    fun `should return false if password has no lowercase letter`() {
        val password = "UPPERCASE1!"
        val result = isValidPassword(password)
        assertFalse(result)
    }

}