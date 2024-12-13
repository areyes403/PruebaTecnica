package com.example.pruebatecnica.organization_feature.domain.usecase

import com.example.pruebatecnica.organization_feature.domain.repository.OrganizationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CheckLocalDataCountTest{
    @RelaxedMockK
    private lateinit var organizationRepository: OrganizationRepository
    private lateinit var checkLocalDataCount: CheckLocalDataCount

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        checkLocalDataCount= CheckLocalDataCount(organizationRepository)
    }

    @Test
    fun `should return data count from repository`() = runBlocking {
        val expectedCount = 5
        coEvery { organizationRepository.checkDataCount() } returns expectedCount
        val result = checkLocalDataCount()
        coVerify(exactly = 1) { organizationRepository.checkDataCount() }
        assertNotNull(result)
    }

}