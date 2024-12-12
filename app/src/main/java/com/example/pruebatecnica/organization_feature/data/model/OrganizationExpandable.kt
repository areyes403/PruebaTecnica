package com.example.pruebatecnica.organization_feature.data.model


data class OrganizationExpandable (
    val id: String,
    val dateInsert: String,
    val slug: String,
    val columns: String,
    val fact: String,
    val organization: String,
    val resource: String,
    val url: String,
    val operations: String,
    val dataset: String,
    val createdAt: String,
    var isExpanded:Boolean=false,
    var isFiltering:Boolean=false
)