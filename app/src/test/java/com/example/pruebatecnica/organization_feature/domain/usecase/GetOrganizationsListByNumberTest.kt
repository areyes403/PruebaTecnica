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
        // Dato simulado que devolverá el DAO
        val expectedData = listOf(
            OrganizationEntity(
                id = "",
                dateInsert = "",
                slug = "",
                columns = "",
                fact = "",
                organization = "",
                resource = "",
                url = "",
                operations = "",
                dataset = "",
                createdAt = 0L
            )
        )
        val paginationSimulated=10

        // Simulamos que el repository llama al DAO y devuelve los datos simulados
        coEvery { organizationRepository.findPaginatedOrganizations(paginationSimulated) } returns expectedData

        // Instanciamos la clase que estamos probando
        val result = getOrganizationsListByNumber(paginationSimulated)

        // Verificamos que el resultado es el esperado
        assertEquals(expectedData,result)

    }
}