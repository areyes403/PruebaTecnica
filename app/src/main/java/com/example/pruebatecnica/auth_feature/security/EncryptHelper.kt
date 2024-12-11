package com.example.pruebatecnica.auth_feature.security

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object EncryptHelper {

    fun encrypt(plainText: String, secretKey: SecretKey): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
    }

    fun decrypt(cipherText: ByteArray, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedData = cipher.doFinal(cipherText)
        return String(decryptedData, Charsets.UTF_8)
    }

}