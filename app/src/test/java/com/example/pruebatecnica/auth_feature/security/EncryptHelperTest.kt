package com.example.pruebatecnica.auth_feature.security

import com.example.pruebatecnica.auth_feature.security.EncryptHelper.aesDecrypt
import com.example.pruebatecnica.auth_feature.security.EncryptHelper.aesEncrypt
import com.example.pruebatecnica.auth_feature.security.EncryptHelper.generateAESKey
import org.junit.Assert.*
import org.junit.Test

class EncryptHelperTest{
    @Test
    fun `Given text when encrypted and decrypted should return original text`() {
        val originalText = "Hello Kotlin AES Encryption!"
        val secretKey = generateAESKey(256)

        val encryptedData = aesEncrypt(originalText.toByteArray(), secretKey)
        val decryptedData = aesDecrypt(encryptedData, secretKey)
        val decryptedText = String(decryptedData)

        assertEquals(originalText, decryptedText, "The decrypted text does not match the original")
    }
}