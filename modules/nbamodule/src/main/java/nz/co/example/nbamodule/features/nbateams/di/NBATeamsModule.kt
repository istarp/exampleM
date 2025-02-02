package nz.co.example.nbamodule.features.nbateams.di

import nz.co.example.nbamodule.features.nbateams.NBATeamsFacade
import nz.co.example.nbamodule.features.nbateams.business.NBATeamsRepository
import nz.co.example.nbamodule.features.nbateams.business.NBATeamsUseCase
import nz.co.example.nbamodule.features.nbateams.data.NBATeamsRepositoryImpl
import nz.co.example.nbamodule.features.nbateams.data.remote.NBATeamsApiService
import org.koin.dsl.module
import retrofit2.Retrofit

internal val nbaTeamsModule = module {
    factory<NBATeamsFacade> { NBATeamsUseCase(get()) }
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(NBATeamsApiService::class.java)
    }
    single<NBATeamsRepository> { NBATeamsRepositoryImpl(get(), get()) }
}