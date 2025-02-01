package nz.co.example.nbamodule.features.database.di

import androidx.room.Room
import nz.co.example.nbamodule.features.nbaplayers.di.nbaPlayersModule
import nz.co.example.nbamodule.features.database.Database
import org.koin.dsl.module

val databaseModule = module {
    includes(nbaPlayersModule)
    single {
        Room.databaseBuilder(
            get(),
            Database::class.java, "nba-database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}