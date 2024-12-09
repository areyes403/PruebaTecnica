package com.example.pruebatecnica.organization_feature.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrganizationEntity(
    @PrimaryKey val id:String,
    val name:String
)
