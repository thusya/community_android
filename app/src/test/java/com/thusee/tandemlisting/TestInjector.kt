package com.thusee.tandemlisting

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

class TestInjector: KoinTest {

    fun start(module: Module) {
        startKoin {
            modules(module)
        }
    }

    fun stop() {
        stopKoin()
    }

}