package com.example.pruebatecnica.auth_feature.domain.model

data class AuthSession (
    val email:String,
    val isAuthenticated:Int=0
)