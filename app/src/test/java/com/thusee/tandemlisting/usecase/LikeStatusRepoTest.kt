package com.thusee.tandemlisting.usecase

import android.content.SharedPreferences
import com.google.gson.Gson
import com.thusee.tandemlisting.TestInjector
import com.thusee.tandemlisting.TestUtils
import com.thusee.tandemlisting.di.appModules
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.koin.dsl.module
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class LikeStatusRepoTest: Spek({

    val mockSharedPreferences: SharedPreferences = mockk(relaxed = true)
    val mockList: ArrayList<Int> = arrayListOf(1,2,3,4)
    val testInjector = TestInjector()
    val mockEditor: SharedPreferences.Editor = mockk(relaxed = true)
    val mockGson: Gson = mockk(relaxed = true)


    lateinit var instance: LikeStatusRepo

    beforeGroup {
        testInjector.start(module {
            single { mockSharedPreferences }
        })
    }
    afterGroup {
        testInjector.stop()
    }

    beforeEachTest {
        instance = spyk(LikeStatusRepo())
        TestUtils.setProperty(instance, "likes", mockList)

        every { mockSharedPreferences.edit() } returns mockEditor
    }
    afterEachTest {
        clearAllMocks()
        unmockkAll()
        mockList.clear()
    }

    describe("add()") {
        it("then add id in the list") {
            val id = 1

            instance.add(id)

            verify {
                instance.save()
            }
            assertEquals(5, mockList.size)

        }
    }
    describe("check()") {
        it("value is contain then return true") {
            val mockDataMapper: CommunityDataMapper = mockk(relaxed = true)

            every { mockDataMapper.id } returns 3

            val result = instance.check(mockDataMapper)

            assertEquals(true, result)
        }
        it("value is not contain then return false") {
            val mockDataMapper: CommunityDataMapper = mockk(relaxed = true)

            every { mockDataMapper.id } returns 5

            val result = instance.check(mockDataMapper)

            assertEquals(false, result)
        }
    }

    describe("save()") {
        it("save list in sharedPreference") {
            every { mockGson.toJson(any()) } returns "test"
            every { mockEditor.putString(any(), any()) } returns mockEditor

            instance.save()

            verify { mockEditor.putString("Test", "[1,2,3,4]") }
            verify { mockEditor.apply() }
        }
    }

})