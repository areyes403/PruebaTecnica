package com.example.pruebatecnica.organization_feature.data.remote.dto

data class ApiResponseDto (
    val pagination: PaginationDto,
    val results: List<ResultDto>
)