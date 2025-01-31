package nz.co.example.coremodule.di

import nz.co.example.coremodule.features.coroutinedispatchers.di.coroutineDispatchersModule
import org.koin.dsl.module

val coreModule = module {
    includes(coroutineDispatchersModule)
}