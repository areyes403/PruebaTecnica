package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetOrganizationsData @Inject constructor(
    private val repository:OrganizationRepository
){
    suspend operator fun invoke()=repository.getData()
}