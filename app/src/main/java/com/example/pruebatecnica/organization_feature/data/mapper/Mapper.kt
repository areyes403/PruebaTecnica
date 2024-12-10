package com.example.pruebatecnica.organization_feature.data.mapper

import com.example.pruebatecnica.organization_feature.data.remote.dto.ApiResponseDto
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import com.example.pruebatecnica.organization_feature.domain.model.OrganizationData
import com.example.pruebatecnica.organization_feature.domain.model.Pagination

fun ApiResponseDto.toOrganizationResponse() = OrganizationData (
    pagination = Pagination(
        pageSize = pagination.pageSize,
        page = pagination.pageSize,
        total = pagination.total
    ) ,
    organizations = results.map {
        Organization(
            id = it._id,
            dateInsert=it.date_insert,
            slug = it.slug,
            columns = it.columns,
            fact = it.fact,
            organization = it.organization,
            resource = it.resource,
            url = it.resource,
            operations = it.operations,
            dataset = it.dataset,
            createdAt = it.created_at
        )
    }
)