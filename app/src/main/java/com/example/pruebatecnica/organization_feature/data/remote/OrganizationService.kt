package com.example.pruebatecnica.organization_feature.data.remote

import com.example.pruebatecnica.organization_feature.data.remote.dto.ApiResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface OrganizationService {
    @GET("gobmx.facts")
    suspend fun getAll():Response<ApiResponseDto>
}