package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import javax.inject.Inject

class InsertOrganization @Inject constructor(
    private val repository: OrganizationRepository
) {
    suspend operator fun invoke(organization:OrganizationEntity)=repository.insertOrganization(organization = organization)
}