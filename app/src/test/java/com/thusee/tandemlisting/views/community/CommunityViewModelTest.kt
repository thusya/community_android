package com.thusee.tandemlisting.views.community

import android.content.SharedPreferences
import com.thusee.tandemlisting.TestInjector
import com.thusee.tandemlisting.usecase.CommunityRemoteRepository
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import com.thusee.tandemlisting.util.LikeStateUtil
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
    val mockLikeStateUtil: LikeStateUtil = mockk(relaxed = true)
    val testInjector = TestInjector()

    beforeGroup {
        testInjector.start(module {
            factory { mockFetchRemoteDataRepository }
            single { mockSharedPreferences }
            single { mockLikeStateUtil }
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
            every { mockLikeStateUtil.check(mockCommunityDataMapper) } returns true

            instance.like(mockCommunityDataMapper)

            verify { mockLikeStateUtil.remove(any()) }

        }
    }

})