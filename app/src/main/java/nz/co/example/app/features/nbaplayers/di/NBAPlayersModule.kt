package nz.co.example.app.features.nbaplayers.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import nz.co.example.app.features.nbaplayers.NBAPlayersViewModel

val nbaPlayersModule = module {
    viewModelOf(::NBAPlayersViewModel)
}