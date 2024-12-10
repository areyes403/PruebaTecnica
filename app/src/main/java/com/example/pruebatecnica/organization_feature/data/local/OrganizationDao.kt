package com.example.pruebatecnica.organization_feature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity

@Dao
interface OrganizationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(organizations:List<OrganizationEntity>)
}