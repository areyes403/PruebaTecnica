package com.example.pruebatecnica.organization_feature.di

import com.example.pruebatecnica.core_feature.data.local.AppDatabase
import com.example.pruebatecnica.organization_feature.data.local.OrganizationDao
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
        organizationService: OrganizationService,
        organizationDao: OrganizationDao
    ):OrganizationRepository=OrganizationRepositoryImpl(organizationService,organizationDao)


    @Provides
    @Singleton
    fun provideOrganizationService(
        retrofit: Retrofit
    ):OrganizationService=retrofit.create(OrganizationService::class.java)

    @Provides
    @Singleton
    fun provideOrganizationDao(
        appDatabase: AppDatabase
    ):OrganizationDao = appDatabase.organizationDao()

}