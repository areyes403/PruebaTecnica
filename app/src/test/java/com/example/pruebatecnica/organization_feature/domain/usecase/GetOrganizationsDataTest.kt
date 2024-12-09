package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.core_feature.data.model.ResponseState
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
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
//        //Given
//        coEvery { organizationRepository.getData() } //returns ResponseState.Success()
//
//        //When
//        getOrganizationsData()
//
//        //Then
//        coVerify(exactly = 1) { organizationRepository.getData() }
    }
}