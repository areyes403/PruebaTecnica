package com.example.pruebatecnica.organization_feature.data.remote.dto

data class ResultDto(
    val _id: String,
    val date_insert: String,
    val slug: String,
    val columns: String,
    val fact: String,
    val organization: String,
    val resource: String,
    val url: String,
    val operations: String,
    val dataset: String,
    val created_at: Long
)
