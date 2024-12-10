package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import javax.inject.Inject

class InsertAllOrganizations @Inject constructor(
    private val organizationRepository: OrganizationRepository
){
    suspend operator fun invoke(organizations:List<Organization>):ResponseState<Unit> = try {
        organizationRepository.insertOrganizations(organizations = organizations)
        ResponseState.Success(Unit)
    }catch (e:Exception){
        ResponseState.Error(e.localizedMessage,null)
    }
}