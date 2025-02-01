package nz.co.example.nbamodule.di

import nz.co.example.nbamodule.features.api.di.apiModule
import nz.co.example.nbamodule.features.database.di.databaseModule
import nz.co.example.nbamodule.features.nbaplayers.di.nbaPlayersModule
import nz.co.example.nbamodule.features.nbateams.di.nbaTeamsModule
import org.koin.dsl.module

val nbaModule = module {
    includes(apiModule, databaseModule, nbaPlayersModule, nbaTeamsModule)
}