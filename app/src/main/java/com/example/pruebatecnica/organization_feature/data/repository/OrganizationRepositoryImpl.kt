package com.example.pruebatecnica.organization_feature.data.repository

import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.util.RepositoryUtil.Companion.retrofitCallWithMapper
import com.example.pruebatecnica.organization_feature.data.local.OrganizationDao
import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationEntities
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationResponse
import com.example.pruebatecnica.organization_feature.data.remote.OrganizationService
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import com.example.pruebatecnica.organization_feature.domain.model.OrganizationData
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OrganizationRepositoryImpl(
    private val service:OrganizationService,
    private val organizationDao: OrganizationDao
):OrganizationRepository {
    override suspend fun getData():ResponseState<OrganizationData> = retrofitCallWithMapper(
        operation = { service.getAll() },
        mapper = { dto -> dto.toOrganizationResponse() }
    )

    override suspend fun insertOrganizations(organizations: List<Organization>) {
        organizationDao.insertAll(organizations = organizations.toOrganizationEntities())
    }

    override suspend fun findPaginatedOrganizations(offset: Int): List<OrganizationEntity> = organizationDao.getListPaged(offset=offset)
}