package com.example.pruebatecnica.organization_feature.data.repository

import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.util.RepositoryUtil.Companion.retrofitCallWithMapper
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationResponse
import com.example.pruebatecnica.organization_feature.data.remote.OrganizationService
import com.example.pruebatecnica.organization_feature.domain.model.OrganizationData
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository

class OrganizationRepositoryImpl(
    private val service:OrganizationService
):OrganizationRepository {
    override suspend fun getData():ResponseState<OrganizationData> = retrofitCallWithMapper(
        operation = { service.getAll() },
        mapper = { dto -> dto.toOrganizationResponse() }
    )
}