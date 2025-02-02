package nz.co.example.nbamodule.features.database.di

import androidx.room.Room
import nz.co.example.nbamodule.features.database.DatabaseFacade
import nz.co.example.nbamodule.features.nbaplayers.data.local.models.TeamConverter
import nz.co.example.nbamodule.features.nbaplayers.di.nbaPlayersModule
import org.koin.dsl.module

val databaseModule = module {
    includes(nbaPlayersModule)
    single {
        Room.databaseBuilder(
            get(),
            DatabaseFacade::class.java, "nba-database"
        ).fallbackToDestructiveMigration()
            .addTypeConverter(TeamConverter())
            .build()
    }
}