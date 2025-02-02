package nz.co.example.coremodule.features.coroutinedispatchers.di

import nz.co.example.coremodule.features.coroutinedispatchers.business.CoroutineDispatchersUseCase
import nz.co.example.coremodule.features.coroutinedispatchers.CoroutineDispatchersFacade
import org.koin.dsl.module

internal val coroutineDispatchersModule = module {
    factory<CoroutineDispatchersFacade> { CoroutineDispatchersUseCase() }
}