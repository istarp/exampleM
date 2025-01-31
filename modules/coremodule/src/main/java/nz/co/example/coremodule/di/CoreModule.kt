package nz.co.example.coremodule.di

import nz.co.example.coremodule.features.coroutinedispatchers.di.coroutineDispatchersModule
import nz.co.example.coremodule.features.network.di.networkModule
import org.koin.dsl.module

val coreModule = module {
    includes(coroutineDispatchersModule, networkModule)
}