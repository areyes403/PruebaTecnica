package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetOrganizationsListByNumber @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(offset:Int) = organizationRepository.findPaginatedOrganizations(offset=offset)
}