package nz.co.example.coremodule.features.coroutinedispatchers.business

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import nz.co.example.coremodule.features.coroutinedispatchers.CoroutineDispatchersFeature

internal class CoroutineDispatchersUseCase : CoroutineDispatchersFeature {

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