package com.example.pruebatecnica.organization_feature.domain.usecase

import androidx.room.ColumnInfo
import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InsertOrganizationTest{
    @RelaxedMockK
    private lateinit var organizationRepository: OrganizationRepository
    private lateinit var insertOrganization: InsertOrganization
    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        insertOrganization= InsertOrganization(organizationRepository)
    }

    @Test
    fun `should call insertOrganization with correct parameters`() = runBlocking{
        val organization=OrganizationEntity(
            id = "",
            dateInsert="",
            slug="",
            columns="",
            fact="",
            organization="",
            resource="",
            url="",
            operations="",
            dataset="",
            createdAt = 0L,
        )

        coEvery { organizationRepository.insertOrganization(any()) } returns Unit
        val result = insertOrganization(organization)
        coVerify (exactly = 1){ organizationRepository.insertOrganization(organization) }
        assert(result == Unit)
    }

}