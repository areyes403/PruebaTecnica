package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.core_feature.data.model.ResponseState
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

class InsertAllOrganizationsTest {

    @RelaxedMockK
    private lateinit var organizationRepository: OrganizationRepository
    private lateinit var insertAllOrganizations: InsertAllOrganizations

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        insertAllOrganizations= InsertAllOrganizations(organizationRepository)
    }

    @Test
    fun `when insertOrganizations is successful then it should return Success`() = runBlocking {
        // Given: Preparamos una lista de organizaciones.
        val organizations = listOf(
            Organization(
                id= "5818ede7bb681ad20c18e617",
                dateInsert= "2016-11-01T19:32:55.363Z",
                slug= "cdi",
                columns= "NA",
                fact= "15 Es el número de bases de datos que la dependencia CDI ha publicado. ",
                organization= "CDI",
                resource= "Número de bases de datos",
                url= "http://datos.gob.mx/busca/organization/cdi",
                operations ="Rutina de R",
                dataset= "Número de bases de datos",
                createdAt = 1461620047
            )
        )

        // Simulamos que el repositorio devuelve un éxito
        coEvery { organizationRepository.insertOrganizations(organizations) } returns Unit

        // When: Llamamos al método del caso de uso.
        val result = insertAllOrganizations(organizations)

        // Then: Verificamos que el repositorio haya sido llamado
        coVerify(exactly = 1) { organizationRepository.insertOrganizations(organizations) }

        // Verificamos que el resultado sea de tipo Success
        assertTrue(result is ResponseState.Success)
    }

    @Test
    fun `when insertOrganizations fails then it should return Error`() = runBlocking {
        val organizations = listOf(
            Organization(
                id= "5818ede7bb681ad20c18e617",
                dateInsert= "2016-11-01T19:32:55.363Z",
                slug= "cdi",
                columns= "NA",
                fact= "15 Es el número de bases de datos que la dependencia CDI ha publicado. ",
                organization= "CDI",
                resource= "Número de bases de datos",
                url= "http://datos.gob.mx/busca/organization/cdi",
                operations ="Rutina de R",
                dataset= "Número de bases de datos",
                createdAt = 1461620047
            )
        )

        // Simulamos que el repositorio lanza una excepción
        coEvery { organizationRepository.insertOrganizations(organizations) } throws Exception("Insertion failed, requires a type OrganizationEntity, found Organization")

        // When: Llamamos al método del caso de uso.
        val result = insertAllOrganizations(organizations)

        // Then: Verificamos que el repositorio haya sido llamado
        coVerify(exactly = 1) { organizationRepository.insertOrganizations(organizations) }

        assertTrue(result is ResponseState.Error)

    }
}