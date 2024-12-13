package com.example.pruebatecnica.auth_feature.domain.model

data class AuthCredentials(
    val email:String,
    val password:String,
    val fcmToken:String?,
    var fingerprint:Int=0
)
