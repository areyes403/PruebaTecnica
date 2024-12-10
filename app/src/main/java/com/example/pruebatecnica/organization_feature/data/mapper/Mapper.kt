package com.example.pruebatecnica.organization_feature.data.mapper

import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import com.example.pruebatecnica.organization_feature.data.model.OrganizationExpandable
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
fun Organization.toOrganizationEntity() = OrganizationEntity(
    id = id,
    dateInsert=dateInsert,
    slug = slug,
    columns = columns,
    fact = fact,
    organization = organization,
    resource = resource,
    url = resource,
    operations = operations,
    dataset = dataset,
    createdAt = createdAt
)

fun OrganizationEntity.toOrganization() = Organization(
    id = id,
    dateInsert=dateInsert,
    slug = slug,
    columns = columns,
    fact = fact,
    organization = organization,
    resource = resource,
    url = resource,
    operations = operations,
    dataset = dataset,
    createdAt = createdAt
)

fun List<Organization>.toOrganizationEntities() = this.map {
    OrganizationEntity(
        id = it.id,
        dateInsert=it.dateInsert,
        slug =it. slug,
        columns = it.columns,
        fact = it.fact,
        organization = it.organization,
        resource = it.resource,
        url = it.resource,
        operations = it.operations,
        dataset = it.dataset,
        createdAt = it.createdAt
    )
}

fun List<Organization>.toOrganizationExpandable() = this.map {
    OrganizationExpandable(
        id = it.id,
        dateInsert=it.dateInsert,
        slug =it. slug,
        columns = it.columns,
        fact = it.fact,
        organization = it.organization,
        resource = it.resource,
        url = it.resource,
        operations = it.operations,
        dataset = it.dataset,
        createdAt = it.createdAt
    )
}