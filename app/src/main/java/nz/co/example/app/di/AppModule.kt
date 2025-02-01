package nz.co.example.app.di

import nz.co.example.app.features.characterdetail.di.characterDetailModule
import nz.co.example.app.features.nbaplayers.di.nbaPlayersModule
import org.koin.dsl.module

val appModule = module {
    includes(nbaPlayersModule, characterDetailModule)
}