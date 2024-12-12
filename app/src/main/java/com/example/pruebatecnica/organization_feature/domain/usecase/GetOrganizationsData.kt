package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.organization_feature.data.mapper.toOrganizationEntity
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetOrganizationsData @Inject constructor(
    private val repository:OrganizationRepository
){
    suspend operator fun invoke():ResponseState<Unit>{
        return if (repository.checkDataCount()==0){
            when (val response=repository.getData()){
                is ResponseState.Success->{
                    try {
                        response.data.organizations.forEach {organization->
                            repository.insertOrganization(organization = organization.toOrganizationEntity())
                        }
                        ResponseState.Success(Unit)
                    }catch (e:Exception){
                        ResponseState.Error("Error al insertar datos",null)
                    }
                }
                is ResponseState.Error->{
                    response
                }
            }
        }else{
            ResponseState.Success(Unit)
        }
    }
}