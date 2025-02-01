package nz.co.example.nbamodule.features.nbaplayers.di

import nz.co.example.nbamodule.features.nbaplayers.NBAPlayersFeature
import nz.co.example.nbamodule.features.nbaplayers.business.NBAPlayersRepository
import nz.co.example.nbamodule.features.nbaplayers.business.NBAPlayersUseCase
import nz.co.example.nbamodule.features.nbaplayers.data.NBAPlayersRepositoryImpl
import nz.co.example.nbamodule.features.nbaplayers.data.remote.NBAPlayersApiService
import org.koin.dsl.module
import retrofit2.Retrofit

internal val nbaPlayersModule = module {
    factory<NBAPlayersFeature> { NBAPlayersUseCase(get()) }
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(NBAPlayersApiService::class.java)
    }
    single<NBAPlayersRepository> { NBAPlayersRepositoryImpl(get(), get(), get()) }
}