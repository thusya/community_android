package com.thusee.tandemlisting.usecase

import androidx.paging.PagingSource
import com.thusee.tandemlisting.TestInjector
import com.thusee.tandemlisting.data.CommunityResponse
import com.thusee.tandemlisting.data.Data
import com.thusee.tandemlisting.data.network.ApiService
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class CommunityRemoteRepositoryTest: Spek({
    val mockApiService: ApiService = mockk(relaxed = true)
    val testInjector = TestInjector()

    lateinit var instance: CommunityRemoteRepository

    beforeGroup {
        testInjector.start(module {
            single { mockApiService }
        })
    }
    afterGroup {
        testInjector.stop()
    }

    beforeEachTest {
        instance = spyk(CommunityRemoteRepository())
    }
    afterEachTest {
        clearAllMocks()
        unmockkAll()
    }

    describe("load()") {
        context("for given params") {
            it("then get the results") {
                val params: PagingSource.LoadParams<Int> = mockk(relaxed = true)
                val mockResponse: CommunityResponse = mockk(relaxed = true)
                val mockData: List<Data> = mockk(relaxed = true)
                every { params.key } returns null
                every {
                    runBlocking {
                        mockApiService.fetchCommunityData(any())
                    }
                } returns mockResponse

                every { mockResponse.data } returns mockData

                runBlocking {
                    val result = instance.load(params)
                    assertEquals(result::class, PagingSource.LoadResult.Page::class)

                }
            }
        }

        context("for given params") {
            it("then get the error") {
                val mockException: Throwable = mockk(relaxed = true)
                val params: PagingSource.LoadParams<Int> = mockk(relaxed = true)
                every {
                    runBlocking {
                        mockApiService.fetchCommunityData(any())
                    }
                } throws mockException

                runBlocking {
                    val result = instance.load(params)
                    assertEquals(result::class, PagingSource.LoadResult.Error::class)
                }

            }
        }
    }

})