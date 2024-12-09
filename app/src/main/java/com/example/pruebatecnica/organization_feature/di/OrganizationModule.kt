package com.example.pruebatecnica.organization_feature.di

import com.example.pruebatecnica.organization_feature.data.remote.OrganizationService
import com.example.pruebatecnica.organization_feature.data.repository.OrganizationRepositoryImpl
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrganizationModule {

    @Provides
    @Singleton
    fun provideOrganizationRepository(
        organizationService: OrganizationService
    ):OrganizationRepository=OrganizationRepositoryImpl(organizationService)


    @Provides
    @Singleton
    fun provideOrganizationService(
        retrofit: Retrofit
    ):OrganizationService=retrofit.create(OrganizationService::class.java)

}