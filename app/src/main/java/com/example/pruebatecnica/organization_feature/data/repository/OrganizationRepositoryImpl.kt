package com.example.pruebatecnica.organization_feature.data.repository

import android.util.Log
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.organization_feature.data.remote.OrganizationService
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository

class OrganizationRepositoryImpl(
    private val service:OrganizationService
):OrganizationRepository {
    override suspend fun getData():ResponseState<Unit> = try {
        val response=service.getAll()
        if (response.isSuccessful){
            Log.i("apiResponse",response.body().toString())
            ResponseState.Success(Unit)
        }else{
            val error=response.errorBody()?.string()
            ResponseState.Error(error,response.code())
        }
    }catch (e:Exception){
        ResponseState.Error("Error",500)
    }
}