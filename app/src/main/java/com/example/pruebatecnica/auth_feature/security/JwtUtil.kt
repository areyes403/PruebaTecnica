package com.example.pruebatecnica.auth_feature.security

import com.example.pruebatecnica.auth_feature.domain.model.AuthSession
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.Date

object JwtUtil {

    /**
     * WARNING MOVE THIS TO PRIVATE
     */
    private val SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924"

    fun generateToken(claims:Map<String,String>,username:String):String{
        return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis()+3600000))
            .signWith(getKey())
            .compact()
    }

    fun getCredentialsPerson(token:String): AuthSession? {
        val claims= getClaims(token = token)
        return if (claims!=null){
            val email=claims.get("email",String::class.java)
            val isAuthenticated=claims.get("authenticated",String::class.java).toInt()
            val authSession=AuthSession(email = email, isAuthenticated = isAuthenticated)
            authSession
        } else null
    }

    private fun getClaims(token:String):Claims?{
        return try {
            val claims=Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                .build()
                .parseSignedClaims(token)
                .payload
            claims
        }catch (e:Exception){
            e.printStackTrace()
            return null
        }
    }

    private fun getKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}