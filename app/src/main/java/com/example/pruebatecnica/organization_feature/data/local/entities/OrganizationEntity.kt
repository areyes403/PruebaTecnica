package com.example.pruebatecnica.organization_feature.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrganizationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:String,
    @ColumnInfo(name = "date_insert")
    val dateInsert: String,
    @ColumnInfo(name = "slug")
    val slug: String,
    @ColumnInfo(name = "columns")
    val columns: String,
    @ColumnInfo(name = "fact")
    val fact: String,
    @ColumnInfo(name = "organization")
    val organization: String,
    @ColumnInfo(name = "resource")
    val resource: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "operations")
    val operations: String,
    @ColumnInfo(name = "dataset")
    val dataset: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long
)
