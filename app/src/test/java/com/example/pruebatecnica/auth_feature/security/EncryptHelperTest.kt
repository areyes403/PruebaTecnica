package com.example.pruebatecnica.auth_feature.security

import org.junit.Assert.*
import org.junit.Test
import javax.crypto.KeyGenerator

class EncryptHelperTest{
    @Test
    fun `Given text when encrypted and decrypted should return original text`() {
        val originalText = "Hello Kotlin AES Encryption!"
        // Generate a secret key
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        val secretKey = keyGenerator.generateKey()

        // Encriptamos el texto
        val encryptedData = EncryptHelper.encrypt(originalText, secretKey)

        // Desencriptamos los datos
        val decryptedData = EncryptHelper.decrypt(encryptedData, secretKey)

        // Comprobamos que el texto desencriptado sea igual al original
        assertEquals(originalText, decryptedData, "The decrypted text does not match the original")
    }
}