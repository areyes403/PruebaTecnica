package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.organization_feature.domain.model.OrganizationData
import com.example.pruebatecnica.organization_feature.domain.model.Pagination
import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetOrganizationsDataTest{

    @RelaxedMockK
    private lateinit var organizationRepository: OrganizationRepository

    private lateinit var getOrganizationsData: GetOrganizationsData

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getOrganizationsData= GetOrganizationsData(organizationRepository)
    }

    @Test
    fun `when the repository returns success then invoke returns success with correct data`() = runBlocking {
        // Given
        val expectedData = OrganizationData(
            pagination = Pagination(
                pageSize = 0,
                page = 0,
                total = 0
            ),
            organizations = listOf()
        )

        coEvery { organizationRepository.getData() } returns ResponseState.Success(expectedData)

        // When
        val result = getOrganizationsData()

        // Then
        coVerify(exactly = 1) { organizationRepository.getData() }
        assert(result is ResponseState.Success)
        val successResult = result as ResponseState.Success
        //assert(successResult.data == expectedData)
    }

    @Test
    fun `when the repository returns any error then invoke returns error`() = runBlocking {
        val expectedCode=400

        val expectedResponse=ResponseState.Error("Bad Request", 400)

        coEvery { organizationRepository.getData() } returns expectedResponse

        val result = getOrganizationsData()

        coVerify(exactly = 1) { organizationRepository.getData() }

        assert(result is ResponseState.Error)

        val errorResult = result as ResponseState.Error

        assert(errorResult.msg == expectedResponse.msg)

        assert(errorResult.code == expectedCode)
    }
}