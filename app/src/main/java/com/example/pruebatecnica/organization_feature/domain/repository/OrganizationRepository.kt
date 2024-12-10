package com.example.pruebatecnica.organization_feature.domain.repository

import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.organization_feature.domain.model.OrganizationData

interface OrganizationRepository {
    suspend fun getData():ResponseState<OrganizationData>
}