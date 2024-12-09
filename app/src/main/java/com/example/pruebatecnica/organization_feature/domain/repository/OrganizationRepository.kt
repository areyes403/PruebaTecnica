package com.example.pruebatecnica.organization_feature.domain.repository

import com.example.pruebatecnica.core_feature.data.model.ResponseState

interface OrganizationRepository {
    suspend fun getData():ResponseState<Unit>
}