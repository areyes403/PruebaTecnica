package com.example.pruebatecnica.core_feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebatecnica.organization_feature.data.local.OrganizationDao
import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity

@Database(entities = [OrganizationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun organizationDao():OrganizationDao
}