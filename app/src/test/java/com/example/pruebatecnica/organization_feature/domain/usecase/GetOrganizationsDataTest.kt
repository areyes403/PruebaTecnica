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
    fun `when the repository returns success then invoke returns success`() = runBlocking {
//        //Given
        coEvery { organizationRepository.getData() } returns ResponseState.Success(Unit)
//
//        //When
        val result = getOrganizationsData()
//
// Then
        coVerify(exactly = 1) { organizationRepository.getData() } // Verifica que el método sea llamado
        assert(result is ResponseState.Success) // Asegúrate de que el resultado es de tipo Success

    }

    @Test
    fun `when the repository returns an error then invoke returns error`() = runBlocking {
        // Given
        coEvery { organizationRepository.getData() } returns ResponseState.Error("Error", 500)

        // When
        val result = getOrganizationsData()

        // Then
        coVerify(exactly = 1) { organizationRepository.getData() } // Verifica que el método sea llamado
        assert(result is ResponseState.Error) // Asegúrate de que el resultado es de tipo Error
        assert((result as ResponseState.Error).msg == "Error") // Verifica el mensaje del error
    }
}