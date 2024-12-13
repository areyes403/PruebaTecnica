package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.organization_feature.data.local.entities.OrganizationEntity
import com.example.pruebatecnica.organization_feature.domain.model.Organization
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetOrganizationsListByNumberTest {

    @RelaxedMockK
    private lateinit var organizationRepository: OrganizationRepository
    private lateinit var getOrganizationsListByNumber: GetOrganizationsListByNumber
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getOrganizationsListByNumber= GetOrganizationsListByNumber(organizationRepository)
    }

    @Test
    fun `test GetOrganizationsDataPaged invokes repository and returns correct data`() = runBlocking {
        val expectedData = listOf<Organization>()
        val paginationSimulated=10

        coEvery { organizationRepository.findPaginatedOrganizations(paginationSimulated) } returns listOf()

        val result = getOrganizationsListByNumber(paginationSimulated)

        assertTrue(expectedData == result)

    }
}