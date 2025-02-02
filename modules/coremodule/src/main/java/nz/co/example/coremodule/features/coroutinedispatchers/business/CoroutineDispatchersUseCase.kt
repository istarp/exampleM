package nz.co.example.coremodule.features.coroutinedispatchers.business

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import nz.co.example.coremodule.features.coroutinedispatchers.CoroutineDispatchersFacade

internal class CoroutineDispatchersUseCase : CoroutineDispatchersFacade {

    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun default(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}