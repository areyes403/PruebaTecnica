package com.example.pruebatecnica.organization_feature.domain.repository

import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import com.example.pruebatecnica.organization_feature.domain.model.OrganizationData
import kotlinx.coroutines.flow.Flow

interface OrganizationRepository {
    suspend fun getData():ResponseState<OrganizationData>
    suspend fun insertOrganizations(organizations:List<Organization>)
    suspend fun findPaginatedOrganizations(offset: Int): List<OrganizationEntity>
}