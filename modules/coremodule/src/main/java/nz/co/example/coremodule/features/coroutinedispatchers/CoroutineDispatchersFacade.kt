package nz.co.example.coremodule.features.coroutinedispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchersFacade {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}