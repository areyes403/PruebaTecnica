package com.example.pruebatecnica.auth_feature.di

import com.example.pruebatecnica.auth_feature.data.repository.AuthRepositoryImpl
import com.example.pruebatecnica.auth_feature.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth:FirebaseAuth
    ):AuthRepository=AuthRepositoryImpl(firebaseAuth = firebaseAuth)

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}