package nz.co.example.app.di

import nz.co.example.app.features.nbaplayerdetail.di.nbaPlayerDetailModule
import nz.co.example.app.features.nbaplayers.di.nbaPlayersModule
import nz.co.example.app.features.nbateamdetail.di.nbaTeamDetailModule
import org.koin.dsl.module

val appModule = module {
    includes(nbaPlayersModule, nbaPlayerDetailModule, nbaTeamDetailModule)
}