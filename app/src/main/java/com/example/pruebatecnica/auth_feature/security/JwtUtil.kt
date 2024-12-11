package com.example.pruebatecnica.auth_feature.security

import com.example.pruebatecnica.auth_feature.domain.model.AuthSession
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.Date

object JwtUtil {

    fun generateToken(claims:Map<String,String>,username:String,secretKey:String):String{
        return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis()+3600000))
            .signWith(getKey(secretKey = secretKey))
            .compact()
    }

    fun getAuthSession(token:String,secretKey:String): AuthSession? {
        val claims= getClaims(token = token, secretKey = secretKey)
        return if (claims!=null){
            val email=claims.get("email",String::class.java)
            val isAuthenticated=claims.get("authenticated",String::class.java).toInt()
            val authSession=AuthSession(email = email, isAuthenticated = isAuthenticated)
            authSession
        } else null
    }

    private fun getClaims(token:String,secretKey:String):Claims?{
        return try {
            val claims=Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .payload
            claims
        }catch (e:Exception){
            e.printStackTrace()
            return null
        }
    }

    private fun getKey(secretKey:String): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}