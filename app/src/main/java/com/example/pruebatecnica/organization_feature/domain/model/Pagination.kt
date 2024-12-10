package com.example.pruebatecnica.organization_feature.domain.model

data class Pagination(
    val pageSize: Int,
    val page: Int,
    val total: Int
)