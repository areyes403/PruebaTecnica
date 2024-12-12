package com.example.pruebatecnica.organization_feature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebatecnica.core_feature.util.Constants
import com.example.pruebatecnica.core_feature.util.Constants.LIMIT_PAGINATION
import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrganizationDao {
    @Query("SELECT COUNT(*) FROM OrganizationEntity")
    suspend fun getTotalCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(organization: OrganizationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(organizations:List<OrganizationEntity>)

    @Query("SELECT * FROM OrganizationEntity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getListPaged(limit: Int = LIMIT_PAGINATION, offset: Int): List<OrganizationEntity>
}