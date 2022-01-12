package com.thusee.tandemlisting.views.community

import android.content.SharedPreferences
import com.thusee.tandemlisting.TestInjector
import com.thusee.tandemlisting.usecase.CommunityRemoteRepository
import com.thusee.tandemlisting.usecase.LikeStatusRepo
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import org.koin.dsl.module
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class CommunityViewModelTest: Spek({

    lateinit var instance: CommunityViewModel
    val mockFetchRemoteDataRepository = mockk<CommunityRemoteRepository>(relaxed = true)
    val mockSharedPreferences: SharedPreferences = mockk(relaxed = true)
    val mockLikeStatusRepo: LikeStatusRepo = mockk(relaxed = true)
    val testInjector = TestInjector()

    beforeGroup {
        testInjector.start(module {
            factory { mockFetchRemoteDataRepository }
            single { mockSharedPreferences }
            single { mockLikeStatusRepo }
        })
    }
    afterGroup {
        testInjector.stop()
    }

    beforeEachTest {
        instance = spyk(CommunityViewModel())
    }

    afterEachTest {
        clearAllMocks()
        unmockkAll()
    }

    describe("like") {
        it("when likeStateUtil.check(dataMapper, return true") {
            val mockCommunityDataMapper: CommunityDataMapper = mockk(relaxed = true)
            every { mockLikeStatusRepo.check(mockCommunityDataMapper) } returns true

            instance.like(mockCommunityDataMapper)

            verify { mockLikeStatusRepo.remove(any()) }

        }
        it("when likeStateUtil.check(dataMapper, return false"){
            val mockCommunityDataMapper: CommunityDataMapper = mockk(relaxed = true)
            every { mockLikeStatusRepo.check(mockCommunityDataMapper) } returns false

            instance.like(mockCommunityDataMapper)

            verify { mockLikeStatusRepo.add(any()) }
        }
    }

})